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
import sys_q.QMS;
import util.Hashing;

public final class IMS {	
	private final QMS dms;
	private final Path output_folder;
	private final Path substance_folder;
	
	IMS(QMS dms, Path of_folder, Path sf_folder) {
		this.dms = dms;
		this.output_folder = of_folder;
		this.substance_folder = sf_folder;
		System.out.println("Sucessfully created the IMS");
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
		
		if (!Filename.isValidFilename(filename)) {
			throw new IllegalArgumentException("Data does not contain valid filename");
		} else if (!Filepath.isValidFilepath(filepath)) {
			throw new IllegalArgumentException("Data does not contain valid filepath");
		} else if (!Filesize.isValidFilesize(filesize)) {
			throw new IllegalArgumentException("Data does not contain valid filesize");
		}
		
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
			dms.saveQuality("System",     "Identity",  identity, "");
			dms.saveQuality("System",     "Substance", identity, hash);
			dms.saveQuality("FileSystem", "Filename",  identity, filename);
			dms.saveQuality("FileSystem", "Filesize",  identity, filesize);
			
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
			dms.saveData("System", identity, data);
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
