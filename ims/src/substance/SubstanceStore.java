package substance;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import util.Hashing;

public final class SubstanceStore {
	private final Path substance_folder;
	
	SubstanceStore(Path substance_folder) {
		this.substance_folder = substance_folder;
		
		System.out.println("Successfully created a substance store");
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
	
	public Path get(String hash) throws IOException {
		if (hash == null) {
			throw new IllegalArgumentException("Hash cannot be null");
		}
		try (DirectoryStream<Path> substances =
				Files.newDirectoryStream(substance_folder)) {
			for (Path substance : substances) {
				if (substance.endsWith(hash)) {
					return substance;
				}
			}
		}
		return null;
	}
	
	public boolean has(String hash) throws IOException {
		if (hash == null) {
			throw new IllegalArgumentException("Hash cannot be null");
		}
		try (DirectoryStream<Path> substances =
				Files.newDirectoryStream(substance_folder)) {
			for (Path substance : substances) {
				if (substance.endsWith(hash)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public String toString() {
		return String.format("Substance Store<Substance Folder<%s>",
				substance_folder);
	}
}
