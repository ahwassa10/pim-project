package sys_r;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import information.Info;

public class RMS {
	private final Path import_folder;
	private final Path output_folder;
	
	RMS(Path if_path, Path of_path) {
		this.import_folder = if_path;
		this.output_folder = of_path;
		System.out.println("Successfully created the RMS");
	}
	
	private List<Info> decompose(Path file) {
		List<Info> infoList = new ArrayList<>();
		
		
		
		return infoList;
	}
	
	public void readImportFolder() {
		try (DirectoryStream<Path> content =
				Files.newDirectoryStream(import_folder)) {
			
			content.forEach(f -> System.out.println(f.getFileName()));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return String.format("RMS<Import Folder<%s>, Output Folder<%s>>",
				import_folder, output_folder);
	}
}
