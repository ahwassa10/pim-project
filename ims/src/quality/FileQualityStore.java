package quality;

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

public final class FileQualityStore implements QualityStore {
    private final Map<String, Set<String>> keyMap;
    private final Path export_folder;
    private final Path quality_folder;

    FileQualityStore(Path ef_path, Path qf_path) {
        this.keyMap = new HashMap<>();
        this.export_folder = ef_path;
        this.quality_folder = qf_path;

        try {
            load();
            System.out.println("Successfully created a quality store");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to create a quality store");
        }
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
    
    public String get(String primaryKey,
                      String secondaryKey) {

        Keys.requireValidKey(primaryKey);
        Keys.requireValidKey(secondaryKey);
        
        if (!containsFullKey(primaryKey, secondaryKey)) {
            return null;
        }
        
        String fullKey = Keys.combine(primaryKey, secondaryKey);
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
    
    public Set<String> primaryKeySet() {
        return Collections.unmodifiableSet(keyMap.keySet());
    }
    
    public void printKeyMap() {
        System.out.println(keyMap);
    }
    
    public String put(String primaryKey,
                      String secondaryKey) {
        
        Keys.requireValidKey(primaryKey);
        Keys.requireValidKey(secondaryKey);
        
        String fullKey = Keys.combine(primaryKey, secondaryKey);
        Path fullKeyPath = quality_folder.resolve(fullKey);
        
        if (containsFullKey(primaryKey, secondaryKey)) {
            try {
                return Files.readString(fullKeyPath);
            } catch (NoSuchFileException e) {
                String message = String.format("%s found in-memory but not on-disk", fullKey);
                System.out.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            keyMap.computeIfAbsent(primaryKey, k -> new HashSet<>())
                  .add(secondaryKey);
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
    
    public String put(String primaryKey,
                      String secondaryKey,
                      String value) {

        Keys.requireValidKey(primaryKey);
        Keys.requireValidKey(secondaryKey);
        Objects.requireNonNull(value, "Value cannot be null");
        
        String fullKey = Keys.combine(primaryKey, secondaryKey);
        Path fullKeyPath = quality_folder.resolve(fullKey);
        
        if (containsFullKey(primaryKey, secondaryKey)) {
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
            keyMap.computeIfAbsent(primaryKey, k -> new HashSet<>())
                  .add(secondaryKey);
            try {
                Files.writeString(fullKeyPath, value);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public String remove(String primaryKey,
                          String secondaryKey) {

        Keys.requireValidKey(primaryKey);
        Keys.requireValidKey(secondaryKey);
        
        String fullKey = Keys.combine(primaryKey, secondaryKey);
        Path fullKeyPath = quality_folder.resolve(fullKey);
        
        if (containsFullKey(primaryKey, secondaryKey)) {
            try {
                String value = Files.readString(fullKeyPath);
                Files.delete(fullKeyPath);
                
                keyMap.get(primaryKey).remove(secondaryKey);
                // Remove the primaryKey as well if it isn't mapped to
                // any secondary keys.
                if (keyMap.get(primaryKey).size() == 0) {
                    keyMap.remove(primaryKey);
                }
                return value;
            } catch (NoSuchFileException e) {
                String message = String.format("%s found in-memory but not on-disk", fullKey);
                System.out.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public Set<String> secondaryKeySet(String primaryKey) {
        Keys.requireValidKey(primaryKey);
        return Collections.unmodifiableSet(keyMap.get(primaryKey));
    }

    public String toString() {
        return String.format("Quality Store<Export Folder<%s>, Quality Folder<%s>>",
                export_folder, quality_folder);
    }
}
