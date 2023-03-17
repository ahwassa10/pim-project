package sys_r;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class RMS {
	private final Path import_folder;
	private final Path output_folder;
	
	public RMS(String if_path, String of_path) {
		import_folder = Path.of(if_path);
		if (!Files.exists(import_folder)) {
			throw new IllegalArgumentException("Import folder path does not exist");
		} else if (!Files.isDirectory(import_folder)) {
			throw new IllegalArgumentException("Import folder path is not a directory");
		}
		
		output_folder = Path.of(of_path);
		if (!Files.exists(output_folder)) {
			throw new IllegalArgumentException("Output folder path does not exist");
		} else if (!Files.isDirectory(output_folder)) {
			throw new IllegalArgumentException("Output folder path is not a directory");
		}
		
		System.out.println("Successfully created the RMS");
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
