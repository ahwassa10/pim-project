package statement;

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

public final class FileStatementStore implements StatementStore {
    private final Map<String, Set<String>> keyMap;
    private final Path export_folder;
    private final Path statement_folder;

    FileStatementStore(Path ef_path, Path sf_path) throws IOException {
        this.keyMap = new HashMap<>();
        this.export_folder = ef_path;
        this.statement_folder = sf_path;
        
        load();
    }
    
    public void clear() {
        keyMap.clear();
        
        try (DirectoryStream<Path> statementStream = Files.newDirectoryStream(statement_folder)) {
            for (Path statementPath : statementStream) {
                if (Files.isRegularFile(statementPath)) {
                    Files.delete(statementPath);
                } else {
                    String message = String.format("%s is not a file", statementPath);
                    System.out.println(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Set<String> holderSetFor(String qualifierKey) {
        Keys.requireValidKey(qualifierKey);
        return Collections.unmodifiableSet(keyMap.get(qualifierKey));
    }
    
    public String get(String qualifierKey,
                      String entityKey) {
        
        if (!containsDescriptor(qualifierKey, entityKey)) {
            return null;
        }
        
        String fullKey = Keys.combine(qualifierKey, entityKey);
        Path fullKeyPath = statement_folder.resolve(fullKey);
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
        try (DirectoryStream<Path> statementStream = Files.newDirectoryStream(statement_folder)) {
            for (Path statementPath : statementStream) {
                if (Files.isRegularFile(statementPath)) {
                    String fullKey = statementPath.getFileName().toString();
                    String[] keys = Keys.split(fullKey);
                    
                    if (keys.length != 2) {
                        String message = String.format("%s is not a valid full key", fullKey);
                        System.out.println(message);
                        continue;
                    }
                    
                    String qualifierKey = keys[0];
                    String holderKey = keys[1];
                    
                    if (!Keys.isValid(qualifierKey)) {
                        String message = String.format("%s is not a valid qualifier key in %s",
                                qualifierKey, fullKey);
                        System.out.println(message);
                        continue;
                    } else if (!Keys.isValid(holderKey)) {
                        String message = String.format("%s is not a valid holder key in %s",
                                holderKey, fullKey);
                        System.out.println(message);
                        continue;
                    }
                    
                    // Add the full key to the keyMap
                    keyMap.computeIfAbsent(qualifierKey, k -> new HashSet<>())
                          .add(holderKey);
                    
                } else {
                    String message = String.format("%s is not a statement", statementPath);
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
                      String holderKey,
                      String value) {

        Keys.requireValidKey(qualifierKey);
        Keys.requireValidKey(holderKey);
        Values.requireValidValue(value);
        
        String fullKey = Keys.combine(qualifierKey, holderKey);
        Path fullKeyPath = statement_folder.resolve(fullKey);
        
        if (containsDescriptor(qualifierKey, holderKey)) {
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
                  .add(holderKey);
            try {
                Files.writeString(fullKeyPath, value);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public String putDescriptor(String qualifierKey,
                                String holderKey) {

        Keys.requireValidKey(qualifierKey);
        Keys.requireValidKey(holderKey);

        String fullKey = Keys.combine(qualifierKey, holderKey);
        Path fullKeyPath = statement_folder.resolve(fullKey);

        if (containsDescriptor(qualifierKey, holderKey)) {
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
                  .add(holderKey);
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
                         String holderKey,
                         Predicate<String> valueTester) {
        Objects.requireNonNull(valueTester, "Value tester predicate cannot be null");
        
        if (!containsDescriptor(qualifierKey, holderKey)) {
            return null;
        }
        
        String fullKey = Keys.combine(qualifierKey, holderKey);
        Path fullKeyPath = statement_folder.resolve(fullKey);
        try {
            String onDisk = Files.readString(fullKeyPath);
            if (valueTester.test(onDisk)) {
                Files.delete(fullKeyPath);
                
                keyMap.get(qualifierKey).remove(holderKey);
                // Remove the qualifier key as well if it isn't mapped to
                // any holder keys.
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
        return String.format("Statement Store<Export Folder<%s>, Statement Folder<%s>>",
                export_folder, statement_folder);
    }
}
