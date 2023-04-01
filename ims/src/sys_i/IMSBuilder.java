package sys_i;

import java.nio.file.Files;
import java.nio.file.Path;

import sys_q.QualityStore;
import sys_q.QualityStoreBuilder;

public class IMSBuilder {
	private QualityStore qualityStore;
	private Path output_folder;
	
	public IMSBuilder() {}
	
	public static IMS test_ims() {
		return new IMSBuilder()
				.setQualityStore(QualityStoreBuilder.test_qms())
				.setOutputFolder("C:\\Users\\Primary\\Desktop\\output")
				.build();
	}
	
	public IMS build() {
		if (qualityStore == null) {
			throw new IllegalStateException("Quality Store not specified");
		} else if (output_folder == null) {
			throw new IllegalStateException("Output folder not specified");
		}
		return new IMS(qualityStore, output_folder);
	}
	
	public IMSBuilder setQualityStore(QualityStore qualityStore) {
		if (qualityStore == null) {
			throw new IllegalArgumentException("Quality Store cannot be null");
		}
		this.qualityStore = qualityStore;
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
}
