package source;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import entity.EntitySystem;
import entity.Identifier;

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

    private static Map<String, String> decompose(Path input_file) throws IOException {
        Map<String, String> info = new HashMap<>();

        info.put("Filepath", input_file.toAbsolutePath().toString());
        info.put("Filename", input_file.getFileName().toString());
        info.put("Filesize", Long.toString((long) Files.getAttribute(input_file, "basic:size")));
        return info;
    }

    public List<Identifier> importAllFiles() {
        List<Identifier> identities = new ArrayList<>();

        try (DirectoryStream<Path> sourceStream = Files.newDirectoryStream(source_folder)) {
            Iterator<Path> sourceFiles = sourceStream.iterator();

            while (sourceFiles.hasNext()) {
                // Import a source file.
                Identifier i = importFile(sourceFiles.next());
                identities.add(i);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return identities;
    }

    public Identifier importFile() {
        try (DirectoryStream<Path> sourceStream = Files.newDirectoryStream(source_folder)) {
            Iterator<Path> sourceFiles = sourceStream.iterator();

            if (sourceFiles.hasNext()) {
                return importFile(sourceFiles.next());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Identifier importFile(Path sourceFile) {
        try {
            String filename = sourceFile.getFileName().toString();
            String filesize = Long.toString((long) Files.getAttribute(sourceFile, "basic:size"));

            Identifier i = entitySystem.createEntity();
            entitySystem.setSubstance(i, sourceFile);
            entitySystem.attribute("filename", i, filename);
            entitySystem.attribute("filesize", i, filesize);

            Path outputFile = output_folder.resolve(filename);
            Files.move(sourceFile, outputFile);
            return i;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void printFiles() {
        try (DirectoryStream<Path> sourceStream = Files.newDirectoryStream(source_folder)) {
            Iterator<Path> sourceFiles = sourceStream.iterator();

            while (sourceFiles.hasNext()) {
                Path sourceFile = sourceFiles.next();
                System.out.println(decompose(sourceFile));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return String.format("File Source<Source Folder<%s>, Output Folder<%s>>",
                source_folder, output_folder);
    }
}
