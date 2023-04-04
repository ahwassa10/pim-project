package quality;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public final class QualityStore {
    private final Map<String, Map<String, Set<String>>> index;
    private final Path export_folder;
    private final Path quality_folder;

    QualityStore(Path ef_path, Path qf_path) {
        this.index = new HashMap<>();
        this.export_folder = ef_path;
        this.quality_folder = qf_path;

        try {
            loadIndex();
            System.out.println("Successfully created a quality store");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to create a quality store");
        }
    }
    /*
    public boolean delete(String agent,
                          String type,
                          String entity) throws IOException {
        
        if (agent == null || type == null || entity == null) {
            throw new IllegalArgumentException("Inputs cannot be null");
        }
        if (!Key.isValid(agent) || !Key.isValid(type) || !Key.isValid(entity)) {
            throw new IllegalArgumentException("Agent, type, and entity need to be valid keys");
        }
        
        Path eventPath = quality_folder.resolve(agent).resolve(type).resolve(entity);
        boolean foundOnDisk = Files.deleteIfExists(eventPath);
        boolean foundInIndex = false;
        
        String qualityType = QualityType.from(agent, type);
        if (index.containsKey(qualityType)) {
            foundInIndex = index.get(qualityType).remove(entity);
        }
        
        if (foundOnDisk != foundInIndex) {
            // Issues a warning when an inconsistency is found between the the disk and 
            // the index. The system recovers from this inconsistency by trying to 
            // delete both the file on disk, and the mapping in the index. 
            System.out.println(String.format("Warning: Event<%s, %s, %s> was found %s",
                    agent, type, entity,
                    foundOnDisk ? "on-disk but not in-index" : "in-index but not on-disk"));
        }
        
        // Returns true as long as something was deleted from either the on-disk or in-index.
        return foundOnDisk || foundInIndex;
    }*/
    
    public void deleteAll() throws IOException {
        index.clear();
        try (Stream<Path> walk = Files.walk(quality_folder, 3)) {
            walk.filter(path -> path.compareTo(quality_folder) != 0)
                .sorted(Comparator.reverseOrder())
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        }
    }
    
    private void loadIndex() throws IOException {
        try (Stream<Path> walk = Files.walk(quality_folder, 3)) {
            walk.forEach(path -> {
                path = quality_folder.relativize(path);
                int count = path.getNameCount();
                
                if (count == 1 && !path.endsWith("")) {
                    // Case where path is an agent
                    String agent = path.toString();
                    index.put(agent, new HashMap<>());
                } else if (count == 2) {
                    // Case where path is a type
                    String agent = path.subpath(0, 1).toString();
                    String type = path.subpath(1, 2).toString();
                    index.get(agent).put(type, new HashSet<>());
                } else if (count == 3) {
                    // Case where path is an entity
                    String agent = path.subpath(0, 1).toString();
                    String type = path.subpath(1, 2).toString();
                    String entity = path.subpath(2, 3).toString();
                    index.get(agent).get(type).add(entity);
                }
            });
        }
    }
    
    
    public void printIndex() {
        System.out.println(index);
    }
    
    /*
    public String retrieve(String agent,
                           String type,
                           String entity) throws IOException {

        if (agent == null || type == null || entity == null) {
            throw new IllegalArgumentException("Inputs cannot be null");
        }
        
        String qualityType = QualityType.from(agent, type);
        if (!index.containsKey(qualityType)) {
            throw new IllegalArgumentException("This qualityType does not exist");
        }
        
        if (!index.get(qualityType).contains(entity)) {
            throw new IllegalArgumentException("The entity does not have this qualityType");
        }
        
        Path dataPath = quality_folder.resolve(agent).resolve(type).resolve(entity); 
        return Files.readString(dataPath);
    }*/

    /*
    public void store(String agent,
                      String type,
                      String entity,
                      String data) throws IOException {

        if (agent == null || type == null || entity == null || data == null) {
            throw new IllegalArgumentException("Inputs cannot be null");
        }
        if (!Key.isValid(agent) || !Key.isValid(type) || !Key.isValid(entity)) {
            throw new IllegalArgumentException("Agent, type, and entity need to be valid keys");
        }

        // Creates the Agent and Type folders if they don't exist.
        // Does nothing if the folders do exist.
        Path typePath = quality_folder.resolve(agent).resolve(type);
        Files.createDirectories(typePath);

        // The UUID of the entity becomes the fileName.
        Path entityPath = typePath.resolve(entity);

        // Creates the file (if it doesn't exist) and writes the string
        // to the file, truncating the file if it already has data in it.
        Files.writeString(entityPath, data);
        
        // Add the agent+type -> entity to the index.
        String qualityType = QualityType.from(agent, type);
        if (index.containsKey(qualityType)) {
            index.get(qualityType).add(entity);
        } else {
            Set<String> entitySet = new HashSet<>();
            entitySet.add(entity);
            index.put(qualityType, entitySet);
        }
    }*/

    public String toString() {
        return String.format("Quality Store<Export Folder<%s>, Quality Folder<%s>>",
                export_folder, quality_folder);
    }
}
