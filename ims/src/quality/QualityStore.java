package quality;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
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
	
	public String loadValue(String agent,
							String quality,
							String entity) throws IOException {
		
		if (agent == null || quality == null || entity == null) {
			throw new IllegalArgumentException("Inputs cannot be null");
		} else if (agent.isBlank() || quality.isBlank() || entity.isBlank()) {
			throw new IllegalArgumentException("Agent, key, and entity cannot be blank");
		}
		
		Path qualityPath = quality_folder.resolve(agent).resolve(quality);
		if (!Files.exists(qualityPath)) {
			throw new IllegalArgumentException("Agent+Quality does not exist");
		}
		Path entityPath = qualityPath.resolve(entity);
		if (!Files.exists(entityPath)) {
			throw new IllegalArgumentException("Entity does not have this quality");
		}
		return Files.readString(entityPath);
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
				
				String key = agent + type;
				index.put(key, entitySet);
			}
		}
	}
	
	public void storeValue(String agent,
						   String quality,
						   String entity,
						   String value) throws IOException {
		
		if (agent == null || quality == null || entity == null || value == null) {
			throw new IllegalArgumentException("Inputs cannot be null");
		}
		if (agent.isBlank() || quality.isBlank() || entity.isBlank()) {
			throw new IllegalArgumentException("Agent, quality, and entity cannot be blank");
		}
		
		// Creates the Agent and InfoType folders if they don't exist.
		// Does nothing if the folders do exist.
		Path qualityPath = quality_folder.resolve(agent).resolve(quality);
		Files.createDirectories(qualityPath);
		
		// The UUID of the entity becomes the fileName.
		Path entityPath = qualityPath.resolve(entity);
		
		// Creates the file (if it doesn't exist) and writes the string
		// to the file, truncating the file if it already has data in it.
		Files.writeString(entityPath, value);
	}
	
	public String toString() {
		return String.format("Quality Store<Export Folder<%s>, Quality Folder<%s>>",
				export_folder, quality_folder);
	}
}
