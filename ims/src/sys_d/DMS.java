package sys_d;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public final class DMS {
	private final Path storage_folder;
	private final Path export_folder;
	
	DMS(Path sf_path, Path ef_path) {
		this.storage_folder = sf_path;
		this.export_folder = ef_path;
		
		System.out.println("Successfully created the DMS");
	}
	
	public Map<String, String> getData(String agent, String qualifier) throws IOException {
		if (qualifier == null) {
			throw new IllegalArgumentException("Input cannot be null");
		} else if (qualifier.isBlank()) {
			throw new IllegalArgumentException("Qualifier cannot be blank");
		}
		
		Map<String, String> data = new HashMap<>();
		
		Path agentPath = storage_folder.resolve(agent);
		
		try (DirectoryStream<Path> keys = Files.newDirectoryStream(agentPath)) {
			for (Path keyPath : keys) {
				Path fileName = keyPath.resolve(qualifier);
				if (Files.exists(fileName)) {
					data.put(keyPath.getFileName().toString(), // Quality key
							 Files.readString(fileName));      // Quality value
				}
			}
		}
		return data;
	}
	
	public String getValue(String agent, String key, String qualifier) throws IOException {
		if (agent == null || key == null || qualifier == null) {
			throw new IllegalArgumentException("Inputs cannot be null");
		}
		if (agent.isBlank() || key.isBlank() || qualifier.isBlank()) {
			throw new IllegalArgumentException("Agent, key, and entity cannot be blank");
		}
		
		Path keyPath = storage_folder.resolve(agent).resolve(key);
		if (!Files.exists(keyPath)) {
			throw new IllegalArgumentException("Agent/Key does not exist");
		}
		Path fileName = keyPath.resolve(qualifier);
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
		Path keyPath = Files.createDirectories(storage_folder.resolve(agent).resolve(key));
		
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
		return String.format("DMS<Storage Folder<%s>, Export Folder<%s>>",
				storage_folder, export_folder);
	}
}
