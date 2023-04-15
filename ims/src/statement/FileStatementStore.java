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
        FileKeys.requireValidKey(qualifierKey);
        return Collections.unmodifiableSet(keyMap.get(qualifierKey));
    }
    
    public String get(String qualifierKey,
                      String holderKey) {
        
        if (!containsDescriptor(qualifierKey, holderKey)) {
            // Ensure that the passed-in keys were valid.
            FileKeys.requireValidKey(qualifierKey);
            FileKeys.requireValidKey(holderKey);
            return null;
        }
        
        String descriptor = FileDescriptors.from(qualifierKey, holderKey);
        Path descriptorPath = statement_folder.resolve(descriptor);
        try {
            return Files.readString(descriptorPath);
        } catch (NoSuchFileException e) {
            String msg = String.format("%s found in-memory but not on-disk", descriptor);
            System.out.println(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private void load() throws IOException {
        try (DirectoryStream<Path> statementStream = Files.newDirectoryStream(statement_folder)) {
            for (Path statementPath : statementStream) {
                if (Files.isRegularFile(statementPath)) {
                    String descriptor = statementPath.getFileName().toString();
                    
                    if (!FileDescriptors.isValid(descriptor)) {
                        String msg = String.format("%s is not a valid file-based descriptor",
                                descriptor);
                        System.out.println(msg);
                    }
                    
                    String qualifierKey = FileDescriptors.getQualifier(descriptor);
                    String holderKey = FileDescriptors.getHolder(descriptor);
                    
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

        FileKeys.requireValidKey(qualifierKey);
        FileKeys.requireValidKey(holderKey);
        FileValues.requireValidValue(value);
        
        String descriptor = FileDescriptors.from(qualifierKey, holderKey);
        Path descriptorPath = statement_folder.resolve(descriptor);
        
        if (containsDescriptor(qualifierKey, holderKey)) {
            try {
                String oldValue = Files.readString(descriptorPath);
                Files.writeString(descriptorPath, value);
                return oldValue;
            } catch (NoSuchFileException e) {
                String msg = String.format("%s found in-memory but not on-disk", descriptor);
                System.out.println(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        } else {
            keyMap.computeIfAbsent(qualifierKey, k -> new HashSet<>())
                  .add(holderKey);
            try {
                Files.writeString(descriptorPath, value);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public String putDescriptor(String qualifierKey,
                                String holderKey) {

        FileKeys.requireValidKey(qualifierKey);
        FileKeys.requireValidKey(holderKey);

        String descriptor = FileDescriptors.from(qualifierKey, holderKey);
        Path descriptorPath = statement_folder.resolve(descriptor);

        if (containsDescriptor(qualifierKey, holderKey)) {
            try {
                return Files.readString(descriptorPath);
            } catch (NoSuchFileException e) {
                String msg = String.format("%s found in-memory but not on-disk", descriptor);
                System.out.println(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            keyMap.computeIfAbsent(qualifierKey, k -> new HashSet<>())
                  .add(holderKey);
            try {
                Files.createFile(descriptorPath);
            } catch (FileAlreadyExistsException e) {
                String msg = String.format("%s found on-disk but not in-memory", descriptor);
                System.out.println(msg);
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
            FileKeys.requireValidKey(qualifierKey);
            FileKeys.requireValidKey(holderKey);
            return null;
        }
        
        String descriptor = FileDescriptors.from(qualifierKey, holderKey);
        Path descriptorPath = statement_folder.resolve(descriptor);
        try {
            String onDisk = Files.readString(descriptorPath);
            if (valueTester.test(onDisk)) {
                Files.delete(descriptorPath);
                
                keyMap.get(qualifierKey).remove(holderKey);
                // Remove the qualifier key as well if it isn't mapped to
                // any holder keys.
                if (keyMap.get(qualifierKey).size() == 0) {
                    keyMap.remove(qualifierKey);
                }
                return onDisk;
            }
        } catch (NoSuchFileException e) {
            String msg = String.format("%s found in-memory but not on-disk", descriptor);
            System.out.println(msg);
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
