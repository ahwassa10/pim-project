package sys_i;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import sys_i.types.Filename;
import sys_i.types.Filepath;
import sys_i.types.Filesize;
import sys_q.QMS;

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
	
	public FileEntity createFileEntity(Map<String, String> data) {
		Objects.requireNonNull(data, "Input data cannot be null");
		
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
		
		UUID identity = UUID.randomUUID();
		data.put("Identity", identity.toString());
		try {
			Path sourcePath = Path.of(filepath);
			Path substancePath = substance_folder.resolve(identity.toString());
			Files.copy(sourcePath, substancePath);
	
			data.put("Filepath", substancePath.toString());
			dms.saveData("FileSystem", identity.toString(), data);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		moveToOutputFolder(Path.of(filepath));
		
		return new SimpleFileEntity(identity, filename, Long.parseLong(filesize));
	}
	
	public String toString() {
		return String.format("IMS<Output Folder<%>, Substance Folder<%s>>",
				output_folder, substance_folder);
	}
}
