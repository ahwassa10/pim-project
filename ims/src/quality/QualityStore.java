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
    private final Map<String, Map<String, Set<String>>> events;
    private final Path export_folder;
    private final Path quality_folder;

    QualityStore(Path ef_path, Path qf_path) {
        this.events = new HashMap<>();
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
        boolean foundInIndex = events.containsKey(agent) && // Checks if agent exists
                               events.get(agent).containsKey(type) && // if qualityType exists
                               events.get(agent).get(type).remove(entity); // if event exists
                               // Note that remove() returns boolean if the entity was removed.
        
        
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
    }
    
    public void deleteAll() throws IOException {
        events.clear();
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
                    events.put(agent, new HashMap<>());
                } else if (count == 2) {
                    // Case where path is a type
                    String agent = path.subpath(0, 1).toString();
                    String type = path.subpath(1, 2).toString();
                    events.get(agent).put(type, new HashSet<>());
                } else if (count == 3) {
                    // Case where path is an entity
                    String agent = path.subpath(0, 1).toString();
                    String type = path.subpath(1, 2).toString();
                    String entity = path.subpath(2, 3).toString();
                    events.get(agent).get(type).add(entity);
                }
            });
        }
    }
    
    public void printIndex() {
        System.out.println(events);
    }
    
    public String retrieve(String agent,
                           String type,
                           String entity) throws IOException {

        if (agent == null || type == null || entity == null) {
            throw new IllegalArgumentException("Inputs cannot be null");
        }
        
        if (!events.containsKey(agent)) {
            throw new IllegalArgumentException("This agent does not exist");
        }
        if (!events.get(agent).containsKey(type)) {
            throw new IllegalArgumentException("This qualityType does not exist");
        }
        if (!events.get(agent).get(type).contains(entity)) {
            throw new IllegalArgumentException("This event does not exist");
        }
        
        Path dataPath = quality_folder.resolve(agent).resolve(type).resolve(entity);
        return Files.readString(dataPath);
    }
    
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
        Path qualityTypePath = quality_folder.resolve(agent).resolve(type);
        Files.createDirectories(qualityTypePath);

        // The UUID of the entity becomes the fileName.
        Path eventPath = qualityTypePath.resolve(entity);

        // Creates the file (if it doesn't exist) and writes the string
        // to the file, truncating the file if it already has data in it.
        Files.writeString(eventPath, data);
        
        // Index the agent if it doesn't exist.
        if (!events.containsKey(agent)) {
            events.put(agent, new HashMap<>());
        }
        // Index the type if it doesn't exist.
        if (!events.get(agent).containsKey(type)) {
            events.get(agent).put(type, new HashSet<>());
        }
        // Create the agent+type -> entity association.
        events.get(agent).get(type).add(entity);
    }

    public String toString() {
        return String.format("Quality Store<Export Folder<%s>, Quality Folder<%s>>",
                export_folder, quality_folder);
    }
}
