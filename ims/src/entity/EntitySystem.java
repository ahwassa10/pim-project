package entity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import entity.types.Filename;
import entity.types.Filepath;
import entity.types.Filesize;
import quality.QualityStore;
import substance.SubstanceStore;

public final class EntitySystem {
	private final Path output_folder;
	private final QualityStore qualityStore;
	private final SubstanceStore substanceStore;
	
	EntitySystem(Path of, QualityStore qs, SubstanceStore ss) {
		this.output_folder = of;
		this.qualityStore = qs;
		this.substanceStore = ss;
		System.out.println("Successfully created an entity system");
	}
	
	public void validateFileData(String filename, String filepath, String filesize) {
		if (!Filename.isValidFilename(filename)) {
			throw new IllegalArgumentException("Data does not contain valid filename");
		} else if (!Filepath.isValidFilepath(filepath)) {
			throw new IllegalArgumentException("Data does not contain valid filepath");
		} else if (!Filesize.isValidFilesize(filesize)) {
			throw new IllegalArgumentException("Data does not contain valid filesize");
		}
	}
	
	public void importFileData(Map<String, String> data) {
		if (data == null) {
			throw new IllegalArgumentException("Input data cannot be null");
		}
		
		// Note that if the map does not contain a key, the get method will return
		// null. This is okay because the isValid... methods return false for null.
		String filename = data.get("Filename");
		String filepath = data.get("Filepath");
		String filesize = data.get("Filesize");
		
		validateFileData(filename, filepath, filesize);
		
		Path file = Path.of(filepath);
		try {
			String hash = substanceStore.capture(file);
			
			String identity = UUID.randomUUID().toString();
			qualityStore.saveQuality("System",     "Identity",  identity, "");
			qualityStore.saveQuality("System",     "Substance", identity, hash);
			qualityStore.saveQuality("FileSystem", "Filename",  identity, filename);
			qualityStore.saveQuality("FileSystem", "Filesize",  identity, filesize);
			
			Path outputFile = output_folder.resolve(filename);
			Files.move(file, outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public SystemEntity createSystemEntity() {
		Map<String, String> data = new HashMap<>();
		UUID uuid = UUID.randomUUID();
		String identity = uuid.toString();
		
		data.put("Identity", identity);
		
		try {
			qualityStore.saveData("System", identity, data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new SimpleSystemEntity(uuid);
	}
	
	public String toString() {
		return String.format("Entity System<Output Folder<%>>", output_folder);
	}
}
