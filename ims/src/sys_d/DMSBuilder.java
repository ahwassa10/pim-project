package sys_d;

import java.nio.file.Files;
import java.nio.file.Path;

public class DMSBuilder {
	private Path export_folder;
	private Path quality_folder;
	private Path substance_folder;
	
	public DMSBuilder() {}
	
	public static DMS test_dms() {
		return new DMSBuilder()
				.setExportFolder("C:\\Users\\Primary\\Desktop\\export")
				.setQualityFolder("C:\\Users\\Primary\\Desktop\\quality")
				.setSubstanceFolder("C:\\Users\\Primary\\Desktop\\substance")
				.build();
	}
	
	public DMS build() {
		if (quality_folder == null) {
			throw new IllegalStateException("Quality folder path not specified");
		} else if (export_folder == null) {
			throw new IllegalStateException("Output folder path not specified");
		}
		return new DMS(export_folder, quality_folder, substance_folder);
	}
	
	public DMSBuilder setExportFolder(String pathname) {
		if (pathname == null) {
			throw new IllegalArgumentException("Export folder path cannot be null");
		}
		Path test_path = Path.of(pathname);
		if (!Files.exists(test_path)) {
			throw new IllegalArgumentException("Export folder does not exist");
		} else if (!Files.isDirectory(test_path)) {
			throw new IllegalArgumentException("Export folder path is not a directory");
		}
		this.export_folder = test_path;
		return this;	
	}
	
	public DMSBuilder setQualityFolder(String pathname) {
		if (pathname == null) {
			throw new IllegalArgumentException("Quality folder path cannot be null");
		}
		Path test_path = Path.of(pathname);
		if (!Files.exists(test_path)) {
			throw new IllegalArgumentException("Quality folder does not exist");
		} else if (!Files.isDirectory(test_path)) {
			throw new IllegalArgumentException("Storage folder path is not a directory");
		}
		this.quality_folder = test_path;
		return this;
	}
	
	public DMSBuilder setSubstanceFolder(String pathname) {
		if (pathname == null) {
			throw new IllegalArgumentException("Substance folder path cannot be null");
		}
		Path test_path = Path.of(pathname);
		if (!Files.exists(test_path)) {
			throw new IllegalArgumentException("Substance folder does not exist");
		} else if (!Files.isDirectory(test_path)) {
			throw new IllegalArgumentException("Substance folder path is not a directory");
		}
		this.substance_folder = test_path;
		return this;
	}
}
