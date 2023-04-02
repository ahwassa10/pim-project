package data;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import entity.EntitySystem;

public final class FileSource {
	private final EntitySystem entitySystem;
	private final Path output_folder;
	private final Path source_folder;
	
	FileSource(EntitySystem entitySystem, Path output_folder, Path source_folder) {
		this.entitySystem = entitySystem;
		this.output_folder = output_folder;
		this.source_folder = source_folder;
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
		try (DirectoryStream<Path> content = Files.newDirectoryStream(source_folder)) {
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
		try (DirectoryStream<Path> content = Files.newDirectoryStream(source_folder)) {
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
		return String.format("File Source<Source Folder<%s>, Output Folder<%s>>",
				source_folder, output_folder);
	}
}
