package sys_d;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public final class DMS {
	private final Path storage_folder;
	private final Path export_folder;
	
	DMS(Path sf_path, Path ef_path) {
		this.storage_folder = sf_path;
		this.export_folder = ef_path;
		
		System.out.println("Successfully created the DMS");
	}
	
	public void saveQuality(String key,
							String qualifier,
							String value) throws IOException {
		
		Path keyPath = null;
		DirectoryStream<Path> contents = Files.newDirectoryStream(storage_folder);
		for (Path p : contents) {
			if (key.equals(p.getFileName().toString())) {
				keyPath = p;
				break;
			}
		}
		if (keyPath == null) {
			keyPath = Files.createDirectory(storage_folder.resolve(key));
		}
		Path fileName = keyPath.resolve(qualifier);
		
		BufferedWriter bw = Files.newBufferedWriter(fileName);
		bw.write(value);
		bw.close();
	}
	
	public String toString() {
		return String.format("DMS<Storage Folder<%s>, Export Folder<%s>>",
				storage_folder, export_folder);
	}
}
