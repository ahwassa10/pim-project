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

public final class IMS {
	private final Path output_folder;
	
	IMS(Path of_folder) {
		this.output_folder = of_folder;
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
		long filesize   = Long.parseLong(data.get("Filesize"));
		
		if (!Filename.isValidFilename(filename)) {
			throw new IllegalArgumentException("Invalid filename value");
		} else if (!Filepath.isValidFilepath(filepath)) {
			throw new IllegalArgumentException("Invalid filepath value");
		} else if (!Filesize.isValidFilesize(filesize)) {
			throw new IllegalArgumentException("Invalid filesize value");
		}
		
		moveToOutputFolder(Path.of(filepath));
		
		UUID identity = UUID.randomUUID();
		
		return new FileEntity() {
			public UUID getIdentity() {
				return identity;
			}
			public String getFilename() {
				return filename;
			}
			public long getFilesize() {
				return filesize;
			}
		};
	}
	
	public String toString() {
		return String.format("IMS<Output Folder<%s>", output_folder);
	}
}
