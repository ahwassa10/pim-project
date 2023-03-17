package sys_d;

import java.nio.file.Files;
import java.nio.file.Path;

public class DMS {
	private final Path storage_folder;
	private final Path export_folder;
	
	public DMS(String sf_pathname, String ef_pathname) {
		storage_folder = Path.of(sf_pathname);
		if (!Files.exists(storage_folder) || !Files.isDirectory(storage_folder)) {
			throw new IllegalArgumentException("Storage folder does not exist");
		}
		
		export_folder  = Path.of(ef_pathname);
		if (!Files.exists(export_folder) || !Files.isDirectory(export_folder)) {
			throw new IllegalArgumentException("Export folder does not exist");
		}
		
		System.out.println("Successfully created the DMS");
	}
}
