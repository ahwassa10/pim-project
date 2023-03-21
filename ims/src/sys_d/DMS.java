package sys_d;

import java.nio.file.Path;

import sys_i.types.Name;
import sys_i.types.Note;

public final class DMS {
	private final Path storage_folder;
	private final Path export_folder;
	private final Types types = new DMSTypes();
	
	DMS(Path sf_path, Path ef_path) {
		this.storage_folder = sf_path;
		this.export_folder = ef_path;
		
		Name.register(this);
		Note.register(this);
		
		System.out.println("Successfully created the DMS");
	}
	
	public Types getTypes() {
		return types;
	}
	
	public String toString() {
		return String.format("DMS<Storage Folder<%s>, Export Folder<%s>>",
				storage_folder, export_folder);
	}
}
