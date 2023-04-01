package data;

import java.nio.file.Files;
import java.nio.file.Path;

public final class FileSourceBuilder {
	private Path import_folder;
	
	public FileSourceBuilder() {}
	
	public static FileSource test_rms() {
		return new FileSourceBuilder()
				.setImportFolder("C:\\Users\\Primary\\Desktop\\import")
				.build();
	}
	
	public FileSourceBuilder setImportFolder(String pathname) {
		if (pathname == null) {
			throw new IllegalArgumentException("Import folder path cannot be null");
		}
		Path test_path = Path.of(pathname);
		if (!Files.exists(test_path)) {
			throw new IllegalArgumentException("Import folder path does not exist");
		} else if (!Files.isDirectory(test_path)) {
			throw new IllegalArgumentException("Import folder path is not a directory");
		}
		import_folder = test_path;
		return this;
	}
	
	public FileSource build() {
		if (import_folder == null) {
			throw new IllegalStateException("Import folder not specified");
		}
		return new FileSource(import_folder);
	}
}
