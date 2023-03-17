package sys_d;

import java.nio.file.Path;

public class DMS {
	private final Path storage_folder;
	private final Path export_folder;
	
	DMS(Path sf_path, Path ef_path) {
		this.storage_folder = sf_path;
		this.export_folder = ef_path;
		System.out.println("Successfully created the DMS");
	}
	
	public String toString() {
		return String.format("DMS<Storage Folder<%s>, Export Folder<%s>>",
				storage_folder, export_folder);
	}
}
