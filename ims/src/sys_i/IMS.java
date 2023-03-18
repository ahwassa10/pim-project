package sys_i;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class IMS {
	private final Path output_folder;
	
	IMS(Path of_folder) {
		this.output_folder = of_folder;
		System.out.println("Sucessfully created the IMS");
	}
	
	private Path moveToOutputFolder(Path input_file) throws IOException {
		Path output_file = output_folder.resolve(input_file.getFileName());
		Files.move(input_file, output_file);
		
		return output_file;
	}
}
