package quality;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public final class QualityStore {
    private final Map<String, Map<String, Set<String>>> keyMap;
    private final Path export_folder;
    private final Path quality_folder;

    QualityStore(Path ef_path, Path qf_path) {
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
    
    public String get(String primaryKey,
                      String secondaryKey,
                      String tertiaryKey) throws IOException {

        Keys.requireValidKey(primaryKey);
        Keys.requireValidKey(secondaryKey);
        Keys.requireValidKey(tertiaryKey);
        
        if (!keyMap.containsKey(primaryKey)) {
          String message = String.format("Primary key: %s does not exist", primaryKey);
          throw new IllegalArgumentException(message);
        }
        if (!keyMap.get(primaryKey).containsKey(secondaryKey)) {
          String message = String.format("Seconday key: %s does not exist", secondaryKey);
          throw new IllegalArgumentException(message);
        }
        if (!keyMap.get(primaryKey).get(secondaryKey).contains(tertiaryKey)) {
          String message = String.format("Tertiary key: %s does not exist", tertiaryKey);
          throw new IllegalArgumentException(message);
        }
        
        String fullKey = Keys.combine(primaryKey, secondaryKey, tertiaryKey);
        Path fullKeyPath = quality_folder.resolve(fullKey);
        return Files.readString(fullKeyPath);
    }
    
    private void load() throws IOException {
        try (DirectoryStream<Path> qualityStream = Files.newDirectoryStream(quality_folder)) {
            for (Path qualityPath : qualityStream) {
                if (Files.isRegularFile(qualityPath)) {
                    String fullKey = qualityPath.getFileName().toString();
                    String[] keys = Keys.split(fullKey);
                    
                    if (keys.length != 3) {
                        String message = String.format("%s is not a valid full key", fullKey);
                        System.out.println(message);
                        continue;
                    }
                    
                    String primaryKey = keys[0];
                    String secondaryKey = keys[1];
                    String tertiaryKey = keys[2];
                    
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
                    } else if (!Keys.isValid(tertiaryKey)) {
                        String message = String.format("%s is not a valid tertiary key in %s",
                                tertiaryKey, fullKey);
                        System.out.println(message);
                        continue;
                    }
                    
                    // Add the full key to the keyMap
                    keyMap.computeIfAbsent(primaryKey, k -> new HashMap<>())
                          .computeIfAbsent(secondaryKey, k -> new HashSet<>())
                          .add(tertiaryKey);
                    
                } else {
                    String message = String.format("%s is not a quality", qualityPath);
                    System.out.println(message);
                }
            }
        }
    }
    
    public void printIndex() {
        System.out.println(keyMap);
    }
    
    public void put(String primaryKey,
                    String secondaryKey,
                    String tertiaryKey,
                    String value) throws IOException {

        Keys.requireValidKey(primaryKey);
        Keys.requireValidKey(secondaryKey);
        Keys.requireValidKey(tertiaryKey);
        
        // Write the quality to disk
        String fullKey = Keys.combine(primaryKey, secondaryKey, tertiaryKey);
        Path fullKeyPath = quality_folder.resolve(fullKey);
        Files.writeString(fullKeyPath, value);
        
        // Add the full key to the keyMap
        keyMap.computeIfAbsent(primaryKey, k -> new HashMap<>())
              .computeIfAbsent(secondaryKey, k -> new HashSet<>())
              .add(tertiaryKey);
    }
    
    public boolean remove(String primaryKey,
                          String secondaryKey,
                          String tertiaryKey) throws IOException {

        Keys.requireValidKey(primaryKey);
        Keys.requireValidKey(secondaryKey);
        Keys.requireValidKey(tertiaryKey);
        
        String fullKey = Keys.combine(primaryKey, secondaryKey, tertiaryKey);
        Path keyPath = quality_folder.resolve(fullKey);
        
        boolean foundOnDisk = Files.deleteIfExists(keyPath);
        boolean foundInMap = keyMap.containsKey(primaryKey) &&
                       keyMap.get(primaryKey).containsKey(secondaryKey) &&
                       keyMap.get(primaryKey).get(secondaryKey).remove(tertiaryKey);
        
        
        if (foundOnDisk != foundInMap) {
        // Issues a warning when an inconsistency is found between the the disk and 
        // the map. The system recovers from this inconsistency by trying to 
        // delete both the file on disk, and the mapping in the index. 
        System.out.println(String.format("Warning: FullKey<%s, %s, %s> was found %s",
              primaryKey, secondaryKey, tertiaryKey,
              foundOnDisk ? "on-disk but not in-map" : "in-map but not on-disk"));
        }
        
        // Returns true as long as something was deleted from either the on-disk or in-index.
        return foundOnDisk || foundInMap;
    }

    public void removeAll() throws IOException {
        keyMap.clear();
        
        try (DirectoryStream<Path> QualityStream = Files.newDirectoryStream(quality_folder)) {
            for (Path qualityPath : QualityStream) {
                if (Files.isRegularFile(qualityPath)) {
                    Files.delete(qualityPath);
                } else {
                    String message = String.format("%s is not a quality", qualityPath);
                    System.out.println(message);
                }
            }
        }
    }

    public String toString() {
        return String.format("Quality Store<Export Folder<%s>, Quality Folder<%s>>",
                export_folder, quality_folder);
    }
}
