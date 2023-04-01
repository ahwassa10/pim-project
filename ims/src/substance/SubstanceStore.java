package substance;

import java.nio.file.Path;

public final class SubstanceStore {
	private final Path substance_folder;
	
	SubstanceStore(Path substance_folder) {
		this.substance_folder = substance_folder;
	}
	
	public String toString() {
		return String.format("Substance Store<Substance Folder<%s>",
				substance_folder);
	}
}
