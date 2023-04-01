package data;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class FileSource implements DataSource {
	private final Path import_folder;
	
	FileSource(Path if_path) {
		this.import_folder = if_path;
		System.out.println("Successfully created a file source");
	}
	
	private Map<String, String> decomposeToInfo(Path input_file) throws IOException {
		Map<String, String> data = new HashMap<>();
		
		data.put("Filepath", input_file.toAbsolutePath().toString());
		data.put("Filename", input_file.getFileName().toString());
		data.put("Filesize", Long.toString((long) Files.getAttribute(input_file, "basic:size")));
		return data;
	}
	
	public Map<String, String> getData() {
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
		return String.format("File Source<Import Folder<%s>>", import_folder);
	}
}
