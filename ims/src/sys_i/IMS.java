package sys_i;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.Map;
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
	
	public static String calculateHash(Path file) throws IOException, NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		
		try (ByteChannel byteChannel = Files.newByteChannel(file)) {
			ByteBuffer buffer = ByteBuffer.allocate(8192);
			while (byteChannel.read(buffer) != -1) {
				buffer.flip();
				digest.update(buffer);
				buffer.clear();
			}
		}
		byte[] hash = digest.digest();
		
		return HexFormat.of().withUpperCase().formatHex(hash);
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
