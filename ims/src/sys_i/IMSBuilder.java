package sys_i;

import java.nio.file.Files;
import java.nio.file.Path;

import sys_q.QMS;
import sys_q.QMSBuilder;

public class IMSBuilder {
	private QMS dms;
	private Path output_folder;
	
	public IMSBuilder() {}
	
	public static IMS test_ims() {
		return new IMSBuilder()
				.setDMS(QMSBuilder.test_qms())
				.setOutputFolder("C:\\Users\\Primary\\Desktop\\output")
				.build();
	}
	
	public IMSBuilder setDMS(QMS dms) {
		if (dms == null) {
			throw new IllegalArgumentException("DMS cannot be null");
		}
		this.dms = dms;
		return this;
	}
	
	public IMSBuilder setOutputFolder(String pathname) {
		if (pathname == null) {
			throw new IllegalArgumentException("Output folder path cannot be null");
		}
		Path test_path = Path.of(pathname);
		if (!Files.exists(test_path)) {
			throw new IllegalArgumentException("Output folder path does not exist");
		} else if (!Files.isDirectory(test_path)) {
			throw new IllegalArgumentException("Output folder path is not a directory");
		}
		output_folder = test_path;
		return this;	
	}
	
	public IMS build() {
		if (dms == null) {
			throw new IllegalStateException("QMS object not specified");
		} else if (output_folder == null) {
			throw new IllegalStateException("Output folder path not specified");
		}
		return new IMS(dms, output_folder);
	}
}
