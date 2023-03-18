package sys_r;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import information.Filename;
import information.Filepath;
import information.Filesize;
import information.Info;

public final class RMS {
	private final Path import_folder;
	
	RMS(Path if_path) {
		this.import_folder = if_path;
		System.out.println("Successfully created the RMS");
	}
	
	private List<Info> decomposeToInfo(Path input_file) throws IOException {
		List<Info> infoList = new ArrayList<>();
		
		infoList.add(Filepath.from(input_file));
		infoList.add(Filename.from(input_file.getFileName().toString()));
		infoList.add(Filesize.from((Long) Files.getAttribute(input_file, "basic:size")));
		
		return infoList;
	}
	
	public List<Info> retrieveInfo() {
		try (DirectoryStream<Path> content = Files.newDirectoryStream(import_folder)) {
			Iterator<Path> i = content.iterator();
			if (i.hasNext()) {
				return decomposeToInfo(i.next());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
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
		return String.format("RMS<Import Folder<%s>", import_folder);
	}
}
