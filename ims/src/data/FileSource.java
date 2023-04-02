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
	
	private static Map<String, String> decomposeToData(Path input_file) throws IOException {
		Map<String, String> data = new HashMap<>();
		
		data.put("Filepath", input_file.toAbsolutePath().toString());
		data.put("Filename", input_file.getFileName().toString());
		data.put("Filesize", Long.toString((long) Files.getAttribute(input_file, "basic:size")));
		return data;
	}
	
	public String importFile() {
		try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(source_folder)) {
			Iterator<Path> content = dirStream.iterator();
			
			if (!content.hasNext()) {
				return null;
			}
			
			Path file = content.next();
			String filename = file.getFileName().toString();
			String filesize = Long.toString((long) Files.getAttribute(file, "basic:size"));
			
			String identity = entitySystem.createEntity();
			entitySystem.setSubstance(identity, file);
			entitySystem.attribute("FileSystem", "Filename", identity, filename);
			entitySystem.attribute("FileSystem", "Filesize", identity, filesize);
			
			Path outputFile = output_folder.resolve(filename);
			Files.move(file, outputFile);
			
			return identity;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void readImportFolder() {
		try (DirectoryStream<Path> content = Files.newDirectoryStream(source_folder)) {
			content.forEach(file -> {
				try {
					System.out.println(decomposeToData(file));
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
