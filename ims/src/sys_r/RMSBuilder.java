package sys_r;

import java.nio.file.Files;
import java.nio.file.Path;

public class RMSBuilder {
	private Path import_folder;
	
	public RMSBuilder() {}
	
	public static RMS test_rms() {
		return new RMSBuilder()
				.setImportFolder("C:\\Users\\Primary\\Desktop\\import")
				.build();
	}
	
	public RMSBuilder setImportFolder(String pathname) {
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
	
	public RMS build() {
		if (import_folder == null) {
			throw new IllegalStateException("Import folder path not specified");
		}
		return new RMS(import_folder);
	}
}
