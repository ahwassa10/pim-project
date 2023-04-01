package sys_i;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import sys_i.types.Filename;
import sys_i.types.Filepath;
import sys_i.types.Filesize;
import sys_q.QualityStore;
import util.Hashing;

public final class IMS {	
	private final QualityStore qms;
	private final Path output_folder;
	private final Path substance_folder;
	
	IMS(QualityStore qms, Path of_folder) {
		this.qms = qms;
		this.output_folder = of_folder;
		System.out.println("Sucessfully created the IMS");
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
		
		Path importFile = Path.of(filepath);
		Path tempFile = substance_folder.resolve("temp");
		try {
			String hash = Hashing.hashStringAndCopy(importFile, tempFile);
			Path substanceFile = substance_folder.resolve(hash);
			
			if (Files.exists(substanceFile)) {
				// The case where the substance is a duplicate
				Files.delete(tempFile);
			} else {
				// Rename the tempFile from "temp" to the string representation
				// of the hash.
				Files.move(tempFile, substanceFile);
			}
			
			String identity = UUID.randomUUID().toString();
			qms.saveQuality("System",     "Identity",  identity, "");
			qms.saveQuality("System",     "Substance", identity, hash);
			qms.saveQuality("FileSystem", "Filename",  identity, filename);
			qms.saveQuality("FileSystem", "Filesize",  identity, filesize);
			
			Path outputFile = output_folder.resolve(filename);
			Files.move(importFile, outputFile);
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
			qms.saveData("System", identity, data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new SimpleSystemEntity(uuid);
	}
	
	public String toString() {
		return String.format("IMS<Output Folder<%>, Substance Folder<%s>>",
				output_folder, substance_folder);
	}
}
