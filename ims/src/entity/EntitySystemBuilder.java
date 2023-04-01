package entity;

import java.nio.file.Files;
import java.nio.file.Path;

import quality.QualityStore;
import quality.QualityStoreBuilder;
import substance.SubstanceStore;
import substance.SubstanceStoreBuilder;

public final class EntitySystemBuilder {
	private Path output_folder;
	private QualityStore qualityStore;
	private SubstanceStore substanceStore;
	
	public EntitySystemBuilder() {}
	
	public static EntitySystem test_ims() {
		return new EntitySystemBuilder()
				.setOutputFolder("C:\\Users\\Primary\\Desktop\\output")
				.setQualityStore(QualityStoreBuilder.test_qms())
				.setSubstanceStore(SubstanceStoreBuilder.test_substore())
				.build();
	}
	
	public EntitySystem build() {
		if (output_folder == null) {
			throw new IllegalStateException("Output folder not specified");
		} else if (qualityStore == null) {
			throw new IllegalStateException("Quality Store not specified");
		} else if (substanceStore == null) {
			throw new IllegalStateException("Substance Store not specified");
		}
		return new EntitySystem(output_folder, qualityStore, substanceStore);
	}
	
	public EntitySystemBuilder setOutputFolder(String pathname) {
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
	
	public EntitySystemBuilder setQualityStore(QualityStore qualityStore) {
		if (qualityStore == null) {
			throw new IllegalArgumentException("Quality Store cannot be null");
		}
		this.qualityStore = qualityStore;
		return this;
	}
	
	public EntitySystemBuilder setSubstanceStore(SubstanceStore substanceStore) {
		if (substanceStore == null) {
			throw new IllegalArgumentException("Substance Store cannot be null");
		}
		this.substanceStore = substanceStore;
		return this;
	}
}
