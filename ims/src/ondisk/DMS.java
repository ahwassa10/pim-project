package ondisk;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class DMS {
	private final Path import_folder;
	private final Path storage_folder;
	private final Path output_folder;
	private final Path export_folder;
	
	public DMS(String if_pathname, String sf_pathname, String of_pathname, String ef_pathname) {
		import_folder  = Path.of(if_pathname);
		if (!Files.exists(import_folder) || !Files.isDirectory(import_folder)) {
			throw new IllegalArgumentException("Import folder does not exist");
		}
		
		storage_folder = Path.of(sf_pathname);
		if (!Files.exists(storage_folder) || !Files.isDirectory(storage_folder)) {
			throw new IllegalArgumentException("Storage folder does not exist");
		}
		
		output_folder  = Path.of(of_pathname);
		if (!Files.exists(output_folder) || !Files.isDirectory(output_folder)) {
			throw new IllegalArgumentException("Output folder does not exist");
		}
		
		export_folder  = Path.of(ef_pathname);
		if (!Files.exists(export_folder) || !Files.isDirectory(export_folder)) {
			throw new IllegalArgumentException("Export folder does not exist");
		}
		
		System.out.println("Successfully created DMS");
	}
	
	public void importFiles() {
		try (DirectoryStream<Path> content =
				Files.newDirectoryStream(import_folder)) {
			
			content.forEach(f -> System.out.println(f.getFileName()));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
