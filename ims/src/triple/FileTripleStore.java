package triple;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

public final class FileTripleStore implements TripleStore {
    private final Map<String, Set<String>> keyMap;
    private final Path export_folder;
    private final Path quality_folder;

    FileTripleStore(Path ef_path, Path qf_path) throws IOException {
        this.keyMap = new HashMap<>();
        this.export_folder = ef_path;
        this.quality_folder = qf_path;
        
        load();
    }
    
    public void clear() {
        keyMap.clear();
        
        try (DirectoryStream<Path> QualityStream = Files.newDirectoryStream(quality_folder)) {
            for (Path qualityPath : QualityStream) {
                if (Files.isRegularFile(qualityPath)) {
                    Files.delete(qualityPath);
                } else {
                    String message = String.format("%s is not a file", qualityPath);
                    System.out.println(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Set<String> entitySetFor(String qualifierKey) {
        Keys.requireValidKey(qualifierKey);
        return Collections.unmodifiableSet(keyMap.get(qualifierKey));
    }
    
    public String get(String qualifierKey,
                      String entityKey) {
        
        if (!containsDescriptor(qualifierKey, entityKey)) {
            return null;
        }
        
        String fullKey = Keys.combine(qualifierKey, entityKey);
        Path fullKeyPath = quality_folder.resolve(fullKey);
        try {
            return Files.readString(fullKeyPath);
        } catch (NoSuchFileException e) {
            String message = String.format("%s found in-memory but not on-disk", fullKey);
            System.out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private void load() throws IOException {
        try (DirectoryStream<Path> qualityStream = Files.newDirectoryStream(quality_folder)) {
            for (Path qualityPath : qualityStream) {
                if (Files.isRegularFile(qualityPath)) {
                    String fullKey = qualityPath.getFileName().toString();
                    String[] keys = Keys.split(fullKey);
                    
                    if (keys.length != 2) {
                        String message = String.format("%s is not a valid full key", fullKey);
                        System.out.println(message);
                        continue;
                    }
                    
                    String primaryKey = keys[0];
                    String secondaryKey = keys[1];
                    
                    if (!Keys.isValid(primaryKey)) {
                        String message = String.format("%s is not a valid primary key in %s",
                                primaryKey, fullKey);
                        System.out.println(message);
                        continue;
                    } else if (!Keys.isValid(secondaryKey)) {
                        String message = String.format("%s is not a valid secondary key in %s",
                                secondaryKey, fullKey);
                        System.out.println(message);
                        continue;
                    }
                    
                    // Add the full key to the keyMap
                    keyMap.computeIfAbsent(primaryKey, k -> new HashSet<>())
                          .add(secondaryKey);
                    
                } else {
                    String message = String.format("%s is not a quality", qualityPath);
                    System.out.println(message);
                }
            }
        }
    }
    
    public Set<String> qualifierSet() {
        return Collections.unmodifiableSet(keyMap.keySet());
    }
    
    public void printKeyMap() {
        System.out.println(keyMap);
    }
    
    public String put(String qualifierKey,
                      String entityKey,
                      String value) {

        Keys.requireValidKey(qualifierKey);
        Keys.requireValidKey(entityKey);
        Values.requireValidValue(value);
        
        String fullKey = Keys.combine(qualifierKey, entityKey);
        Path fullKeyPath = quality_folder.resolve(fullKey);
        
        if (containsDescriptor(qualifierKey, entityKey)) {
            try {
                String oldValue = Files.readString(fullKeyPath);
                Files.writeString(fullKeyPath, value);
                return oldValue;
            } catch (NoSuchFileException e) {
                String message = String.format("%s found in-memory but not on-disk", fullKey);
                System.out.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            keyMap.computeIfAbsent(qualifierKey, k -> new HashSet<>())
                  .add(entityKey);
            try {
                Files.writeString(fullKeyPath, value);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public String putDescriptor(String qualifierKey,
                                String entityKey) {

        Keys.requireValidKey(qualifierKey);
        Keys.requireValidKey(entityKey);

        String fullKey = Keys.combine(qualifierKey, entityKey);
        Path fullKeyPath = quality_folder.resolve(fullKey);

        if (containsDescriptor(qualifierKey, entityKey)) {
            try {
                return Files.readString(fullKeyPath);
            } catch (NoSuchFileException e) {
                String message = String.format("%s found in-memory but not on-disk", fullKey);
                System.out.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            keyMap.computeIfAbsent(qualifierKey, k -> new HashSet<>())
                  .add(entityKey);
            try {
                Files.createFile(fullKeyPath);
            } catch (FileAlreadyExistsException e) {
                String message = String.format("%s found on-disk but not in-memory", fullKey);
                System.out.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public String remove(String qualifierKey,
                         String entityKey,
                         Predicate<String> valueTester) {
        Objects.requireNonNull(valueTester, "Value tester predicate cannot be null");
        
        if (!containsDescriptor(qualifierKey, entityKey)) {
            return null;
        }
        
        String fullKey = Keys.combine(qualifierKey, entityKey);
        Path fullKeyPath = quality_folder.resolve(fullKey);
        try {
            String onDisk = Files.readString(fullKeyPath);
            if (valueTester.test(onDisk)) {
                Files.delete(fullKeyPath);
                
                keyMap.get(qualifierKey).remove(entityKey);
                // Remove the qualifier key as well if it isn't mapped to
                // any entity keys.
                if (keyMap.get(qualifierKey).size() == 0) {
                    keyMap.remove(qualifierKey);
                }
                return onDisk;
            }
        } catch (NoSuchFileException e) {
            String message = String.format("%s found in-memory but not on-disk", fullKey);
            System.out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String toString() {
        return String.format("Triple Store<Export Folder<%s>, Quality Folder<%s>>",
                export_folder, quality_folder);
    }
}
