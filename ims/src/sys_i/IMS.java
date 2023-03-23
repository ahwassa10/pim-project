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
	
	private Path moveToOutputFolder(Path input_file) {
		Path output_file = output_folder.resolve(input_file.getFileName());
		try {
			Files.move(input_file, output_file);
		} catch (IOException e) {
			e.printStackTrace();
			return input_file;
		}
		return output_file;
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
		
		Path sourceFile = Path.of(filepath);
		Path tempFile = substance_folder.resolve("temp");
		String hash = "";
		try {
			hash = Hashing.hashStringAndCopy(sourceFile, tempFile);
			Path destFile = substance_folder.resolve(hash);
			
			if (Files.exists(destFile)) {
				// The case where the substance is a duplicate
				Files.delete(tempFile);
			} else {
				Files.move(tempFile, substance_folder.resolve(hash));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		String identity = UUID.randomUUID().toString();
		
		try {
			dms.saveQuality("System",     "Identity",  identity, "");
			dms.saveQuality("System",     "Substance", identity, hash);
			dms.saveQuality("FileSystem", "Filename",  identity, filename);
			dms.saveQuality("FileSystem", "Filesize",  identity, filesize);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public FileEntity createFileEntity(Map<String, String> data) {
		if (data == null) {
			throw new IllegalArgumentException("Input data cannot be null");
		}
		
		if (!data.containsKey("Filename")) {
			throw new IllegalArgumentException("Data does not contain Filename attribute");
		} else if (!data.containsKey("Filepath")) {
			throw new IllegalArgumentException("Data does not contain Filepath attribute");
		} else if (!data.containsKey("Filesize")) {
			throw new IllegalArgumentException("Data does not contain Filesize attribute");
		}
		
		String filename = data.get("Filename");
		String filepath = data.get("Filepath");
		String filesize = data.get("Filesize");
		
		if (!Filename.isValidFilename(filename)) {
			throw new IllegalArgumentException("Invalid filename value");
		} else if (!Filepath.isValidFilepath(filepath)) {
			throw new IllegalArgumentException("Invalid filepath value");
		} else if (!Filesize.isValidFilesize(filesize)) {
			throw new IllegalArgumentException("Invalid filesize value");
		}
		
		SystemEntity systemEntity = createSystemEntity();
		String identity = systemEntity.getIdentity().toString();
		try {
			Path sourcePath = Path.of(filepath);
			Path substancePath = substance_folder.resolve(identity);
			Files.copy(sourcePath, substancePath);
	
			data.put("Filepath", substancePath.toString());
			dms.saveData("FileSystem", identity, data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		moveToOutputFolder(Path.of(filepath));
		
		return new SimpleFileEntity(systemEntity, filename, Long.parseLong(filesize));
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
