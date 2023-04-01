package sys_i;

import java.nio.file.Files;
import java.nio.file.Path;

import substance.SubstanceStore;
import substance.SubstanceStoreBuilder;
import sys_q.QualityStore;
import sys_q.QualityStoreBuilder;

public final class IMSBuilder {
	private Path output_folder;
	private QualityStore qualityStore;
	private SubstanceStore substanceStore;
	
	public IMSBuilder() {}
	
	public static IMS test_ims() {
		return new IMSBuilder()
				.setOutputFolder("C:\\Users\\Primary\\Desktop\\output")
				.setQualityStore(QualityStoreBuilder.test_qms())
				.setSubstanceStore(SubstanceStoreBuilder.test_substore())
				.build();
	}
	
	public IMS build() {
		if (output_folder == null) {
			throw new IllegalStateException("Output folder not specified");
		} else if (qualityStore == null) {
			throw new IllegalStateException("Quality Store not specified");
		} else if (substanceStore == null) {
			throw new IllegalStateException("Substance Store not specified");
		}
		return new IMS(output_folder, qualityStore, substanceStore);
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
	
	public IMSBuilder setQualityStore(QualityStore qualityStore) {
		if (qualityStore == null) {
			throw new IllegalArgumentException("Quality Store cannot be null");
		}
		this.qualityStore = qualityStore;
		return this;
	}
	
	public IMSBuilder setSubstanceStore(SubstanceStore substanceStore) {
		if (substanceStore == null) {
			throw new IllegalArgumentException("Substance Store cannot be null");
		}
		this.substanceStore = substanceStore;
		return this;
	}
}
