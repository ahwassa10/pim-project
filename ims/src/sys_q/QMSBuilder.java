package sys_q;

import java.nio.file.Files;
import java.nio.file.Path;

public class QMSBuilder {
	private Path export_folder;
	private Path quality_folder;
	
	public QMSBuilder() {}
	
	public static QMS test_qms() {
		return new QMSBuilder()
				.setExportFolder("C:\\Users\\Primary\\Desktop\\export")
				.setQualityFolder("C:\\Users\\Primary\\Desktop\\quality")
				.build();
	}
	
	public QMS build() {
		if (quality_folder == null) {
			throw new IllegalStateException("Quality folder not specified");
		} else if (export_folder == null) {
			throw new IllegalStateException("Output folder not specified");
		}
		return new QMS(export_folder, quality_folder);
	}
	
	public QMSBuilder setExportFolder(String pathname) {
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
	
	public QMSBuilder setQualityFolder(String pathname) {
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
}
