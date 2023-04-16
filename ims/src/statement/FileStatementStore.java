package statement;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    
    public Set<String> holderSetFor(String qualifierKey) {
        FileKeys.requireValidKey(qualifierKey);
        return Collections.unmodifiableSet(keyMap.get(qualifierKey));
    }
    
    public boolean isValidKey(String test_key) {
        return FileKeys.isValid(test_key);
    }
    
    public boolean isValidValue(String test_value) {
        return FileValues.isValid(test_value);
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
    
    public void printKeyMap() {
        System.out.println(keyMap);
    }
    
    public String put(String qualifierKey,
                      String holderKey,
                      String valueOrNull) {

        FileKeys.requireValidKey(qualifierKey);
        FileKeys.requireValidKey(holderKey);
        
        if (valueOrNull != null) {
            FileValues.requireValidValue(valueOrNull);
        }
        
        String descriptor = FileDescriptors.from(qualifierKey, holderKey);
        Path descriptorPath = statement_folder.resolve(descriptor);
        
        if (containsDescriptor(qualifierKey, holderKey)) {
            try {
                String oldValue = Files.readString(descriptorPath);
                if (valueOrNull != null) {
                    Files.writeString(descriptorPath, valueOrNull);
                }
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
                Files.writeString(descriptorPath, valueOrNull,
                        StandardOpenOption.CREATE_NEW,
                        StandardOpenOption.WRITE);
            } catch (FileAlreadyExistsException e) {
                String msg = String.format("%s found on-disk but not in-memory", descriptor);
                System.out.println(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public Set<String> qualifierSet() {
        return Collections.unmodifiableSet(keyMap.keySet());
    }
    
    public void remove(String qualifierKey,
                       String holderKey) {
        
        if (!containsDescriptor(qualifierKey, holderKey)) {
            FileKeys.requireValidKey(qualifierKey);
            FileKeys.requireValidKey(holderKey);
            return;
        }
        
        String descriptor = FileDescriptors.from(qualifierKey, holderKey);
        Path descriptorPath = statement_folder.resolve(descriptor);
        try {
            Files.delete(descriptorPath);
            keyMap.get(qualifierKey).remove(holderKey);
            // Remove the qualifier key as well if it isn't mapped to
            // any holder keys.
            if (keyMap.get(qualifierKey).size() == 0) {
                keyMap.remove(qualifierKey);
            }
        } catch (NoSuchFileException e) {
            String msg = String.format("%s found in-memory but not on-disk", descriptor);
            System.out.println(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return String.format("Statement Store<Export Folder<%s>, Statement Folder<%s>>",
                export_folder, statement_folder);
    }
}
