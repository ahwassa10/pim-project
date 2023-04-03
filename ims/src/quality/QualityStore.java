package quality;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class QualityStore {
    private final Map<String, Set<String>> index;
    private final Path export_folder;
    private final Path quality_folder;

    QualityStore(Path ef_path, Path qf_path) {
        this.index = new HashMap<>();
        this.export_folder = ef_path;
        this.quality_folder = qf_path;

        try {
            readFromDisk();
            System.out.println("Successfully created a quality store");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to create a quality store");
        }
    }

    public String loadData(String agent,
                           String type,
                           String entity) throws IOException {

        if (agent == null || type == null || entity == null) {
            throw new IllegalArgumentException("Inputs cannot be null");
        }
        
        String qualityType = agent+type;
        if (!index.containsKey(qualityType)) {
            throw new IllegalArgumentException("This qualityType does not exist");
        }
        
        if (!index.get(qualityType).contains(entity)) {
            throw new IllegalArgumentException("The entity does not have this qualityType");
        }
        
        Path dataPath = quality_folder.resolve(agent).resolve(type).resolve(entity); 
        return Files.readString(dataPath);
    }

    private void readFromDisk() throws IOException {
        for (Path agentPath : Files.newDirectoryStream(quality_folder)) {
            String agent = agentPath.getFileName().toString();

            for (Path typePath : Files.newDirectoryStream(agentPath)) {
                String type = typePath.getFileName().toString();
                
                Set<String> entitySet = new HashSet<>();
                for (Path entityPath : Files.newDirectoryStream(typePath)) {
                    String entity = entityPath.getFileName().toString();
                    entitySet.add(entity);
                }

                String qualityType = agent+type;
                index.put(qualityType, entitySet);
            }
        }
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
        Path typePath = quality_folder.resolve(agent).resolve(type);
        Files.createDirectories(typePath);

        // The UUID of the entity becomes the fileName.
        Path entityPath = typePath.resolve(entity);

        // Creates the file (if it doesn't exist) and writes the string
        // to the file, truncating the file if it already has data in it.
        Files.writeString(entityPath, data);
    }

    public String toString() {
        return String.format("Quality Store<Export Folder<%s>, Quality Folder<%s>>",
                export_folder, quality_folder);
    }
}
