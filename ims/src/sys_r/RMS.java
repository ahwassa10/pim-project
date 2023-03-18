package sys_r;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import information.Filename;
import information.Filepath;
import information.Filesize;
import information.Info;

public class RMS {
	private final Path import_folder;
	private final Path output_folder;
	
	RMS(Path if_path, Path of_path) {
		this.import_folder = if_path;
		this.output_folder = of_path;
		System.out.println("Successfully created the RMS");
	}
	
	private List<Info> decomposeToInfo(Path input_file) throws IOException {
		List<Info> infoList = new ArrayList<>();
		
		infoList.add(Filepath.from(input_file));
		infoList.add(Filename.from(input_file.getFileName().toString()));
		infoList.add(Filesize.from((Long) Files.getAttribute(input_file, "basic:size")));
		
		return infoList;
	}
	
	private Path moveToOutputFolder(Path input_file) throws IOException {
		Path output_file = output_folder.resolve(input_file.getFileName());
		Files.move(input_file, output_file);
		
		return output_file;
	}
	
	public void readImportFolder() {
		try (DirectoryStream<Path> content = Files.newDirectoryStream(import_folder)) {
			content.forEach(file -> {
				try {
					System.out.println(decomposeToInfo(file));
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return String.format("RMS<Import Folder<%s>, Output Folder<%s>>",
				import_folder, output_folder);
	}
}
