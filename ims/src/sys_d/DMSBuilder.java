package sys_d;

import java.nio.file.Files;
import java.nio.file.Path;

public class DMSBuilder {
	private Path storage_folder;
	private Path export_folder;
	
	public DMSBuilder() {}
	
	public static DMS test_dms() {
		return new DMSBuilder()
				.setStorageFolder("C:\\Users\\Primary\\Desktop\\storage")
				.setExportFolder("C:\\Users\\Primary\\Desktop\\export")
				.build();
	}
	
	public DMSBuilder setStorageFolder(String pathname) {
		if (pathname == null) {
			throw new IllegalArgumentException("Storage folder path cannot be null");
		}
		Path test_path = Path.of(pathname);
		if (!Files.exists(test_path)) {
			throw new IllegalArgumentException("Storage folder path does not exist");
		} else if (!Files.isDirectory(test_path)) {
			throw new IllegalArgumentException("Storage folder path is not a directory");
		}
		storage_folder = test_path;
		return this;
	}
	
	public DMSBuilder setExportFolder(String pathname) {
		if (pathname == null) {
			throw new IllegalArgumentException("Export folder path cannot be null");
		}
		Path test_path = Path.of(pathname);
		if (!Files.exists(test_path)) {
			throw new IllegalArgumentException("Export folder path does not exist");
		} else if (!Files.isDirectory(test_path)) {
			throw new IllegalArgumentException("Export folder path is not a directory");
		}
		export_folder = test_path;
		return this;	
	}
	
	public DMS build() {
		if (storage_folder == null) {
			throw new IllegalStateException("Storage folder path not specified");
		} else if (export_folder == null) {
			throw new IllegalStateException("Output folder path not specified");
		}
		return new DMS(storage_folder, export_folder);
	}
}
