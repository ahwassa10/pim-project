package sys_q;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class QualityStore {
	private final Path export_folder;
	private final Path quality_folder;
	
	QualityStore(Path ef_path, Path qf_path) {
		this.export_folder = ef_path;
		this.quality_folder = qf_path;
		
		System.out.println("Successfully created the QMS");
	}
	
	public Map<String, String> getData(String agent, String entity) throws IOException {
		if (agent == null || entity == null) {
			throw new IllegalArgumentException("Inputs cannot be null");
		} else if (agent.isBlank() || entity.isBlank()) {
			throw new IllegalArgumentException("Agent and entity cannot be blank");
		}
		
		Map<String, String> data = new HashMap<>();
		Path agentPath = quality_folder.resolve(agent);
		
		try (DirectoryStream<Path> keys = Files.newDirectoryStream(agentPath)) {
			for (Path keyPath : keys) {
				Path fileName = keyPath.resolve(entity);
				if (Files.exists(fileName)) {
					data.put(keyPath.getFileName().toString(), // Quality key
							 Files.readString(fileName));      // Quality value
				}
			}
		}
		return data;
	}
	
	public void saveData(String agent, String entity, Map<String, String> data) throws IOException {
		if (agent == null || entity == null || data == null) {
			throw new IllegalArgumentException("Inputs cannot be null");
		} else if (agent.isBlank() || entity.isBlank()) {
			throw new IllegalArgumentException("Agent and entity cannot be blank");
		}
		
		Path agentPath = Files.createDirectories(quality_folder.resolve(agent));
		for (Entry<String, String> dataEntry : data.entrySet()) {
			String key = dataEntry.getKey();
			String value = dataEntry.getValue();
			
			Path keyPath = Files.createDirectories(agentPath.resolve(key));
			Path fileName = keyPath.resolve(entity);
			Files.writeString(fileName, value);
		}
	}
	
	public String getQuality(String agent, String key, String entity) throws IOException {
		if (agent == null || key == null || entity == null) {
			throw new IllegalArgumentException("Inputs cannot be null");
		} else if (agent.isBlank() || key.isBlank() || entity.isBlank()) {
			throw new IllegalArgumentException("Agent, key, and entity cannot be blank");
		}
		
		Path keyPath = quality_folder.resolve(agent).resolve(key);
		if (!Files.exists(keyPath)) {
			throw new IllegalArgumentException("Agent/Key does not exist");
		}
		Path fileName = keyPath.resolve(entity);
		if (!Files.exists(fileName)) {
			throw new IllegalArgumentException("Qualifier does not have this key");
		}
		return Files.readString(fileName);
	}
	
	public void saveQuality(String agent,
							String key,
							String entity,
							String value) throws IOException {
		
		if (agent == null || key == null || entity == null || value == null) {
			throw new IllegalArgumentException("Inputs cannot be null");
		}
		if (agent.isBlank() || key.isBlank() || entity.isBlank()) {
			throw new IllegalArgumentException("Agent, key, and entity cannot be blank");
		}
		
		// Creates the Agent and InfoType folders if they don't exist.
		// Does nothing if the folders do exist.
		Path keyPath = Files.createDirectories(quality_folder.resolve(agent).resolve(key));
		
		// The UUID of the entity becomes the fileName.
		Path fileName = keyPath.resolve(entity);
		
		// Creates the file (if it doesn't exist) and writes the string
		// to the file, truncating the file if it already has data in it.
		Files.writeString(fileName, value);
	}
	
	public void saveQuality(Quality q) throws IOException {
		saveQuality(q.getAgent(), q.getKey(), q.getEntity(), q.getValue());
	}
	
	public String toString() {
		return String.format("Quality Store<Export Folder<%s>, Quality Folder<%s>>",
				export_folder, quality_folder);
	}
}
