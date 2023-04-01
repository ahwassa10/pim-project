package substance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import util.Hashing;

public final class SubstanceStore {
	private final Path substance_folder;
	
	SubstanceStore(Path substance_folder) {
		this.substance_folder = substance_folder;
	}
	
	public String capture(Path substanceSource) {
		Path tempFile = substance_folder.resolve("temp");		
		try {
			String hash = Hashing.hashStringAndCopy(substanceSource, tempFile);
			Path substanceFile = substance_folder.resolve(hash);
			
			if (Files.exists(substanceFile)) {
				// The case where the substance is a duplicate
				Files.delete(tempFile);
			} else {
				// Rename the tempFile from "temp" to the string representation
				// of the hash.
				Files.move(tempFile, substanceFile);
			}
			// Whether the substance is a duplicate or not, we always return
			// a hash of the substance.
			return hash;
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String toString() {
		return String.format("Substance Store<Substance Folder<%s>",
				substance_folder);
	}
}
