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
	
	public Map<String, String> getData(String qualifier) throws IOException {
		if (qualifier == null) {
			throw new IllegalArgumentException("Input cannot be null");
		} else if (qualifier.isBlank()) {
			throw new IllegalArgumentException("Qualifier cannot be blank");
		}
		
		Map<String, String> data = new HashMap<>();
		
		try (DirectoryStream<Path> keys = Files.newDirectoryStream(storage_folder)) {
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
	
	public String getValue(String key, String qualifier) throws IOException {
		if (key == null || qualifier == null) {
			throw new IllegalArgumentException("Inputs cannot be null");
		}
		if (key.isBlank() || qualifier.isBlank()) {
			throw new IllegalArgumentException("Key and qualifier cannot be blank");
		}
		
		Path keyPath = storage_folder.resolve(key);
		if (!Files.exists(keyPath)) {
			throw new IllegalArgumentException("Key does not exist");
		}
		Path fileName = keyPath.resolve(qualifier);
		if (!Files.exists(fileName)) {
			throw new IllegalArgumentException("Qualifier does not have this key");
		}
		return Files.readString(fileName);
	}
	
	public void saveQuality(String key,
							String qualifier,
							String value) throws IOException {
		
		if (key == null || qualifier == null || value == null) {
			throw new IllegalArgumentException("Inputs cannot be null");
		}
		if (key.isBlank() || qualifier.isBlank()) {
			throw new IllegalArgumentException("Key and qualifier cannot be blank");
		}
		
		// Creates the InfoType folder if it doesn't exist.
		// Does nothing if the folder does exist.
		Path keyPath = Files.createDirectories(storage_folder.resolve(key));
		
		// The UUID of the entity becomes the fileName.
		Path fileName = keyPath.resolve(qualifier);
		
		// Creates the file (if it doesn't exist) and writes the string
		// to the file, truncating the file if it already has data in it.
		Files.writeString(fileName, value);
	}
	
	public void saveQuality(Quality q) throws IOException {
		saveQuality(q.getKey(), q.getQualifier(), q.getValue());
	}
	
	public String toString() {
		return String.format("DMS<Storage Folder<%s>, Export Folder<%s>>",
				storage_folder, export_folder);
	}
}
