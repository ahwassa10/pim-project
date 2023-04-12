package source;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import entity.EntitySystem;

public final class FileSourceBuilder {
    private EntitySystem entitySystem;
    private Path output_folder;
    private Path source_folder;

    public FileSourceBuilder() {
    }

    public static FileSource test_rms(EntitySystem entitySystem) {
        return new FileSourceBuilder()
                .setEntitySystem(entitySystem)
                .setOutputFolder("C:\\Users\\Primary\\Desktop\\output")
                .setSourceFolder("C:\\Users\\Primary\\Desktop\\source")
                .build();
    }

    public FileSource build() {
        if (entitySystem == null) {
            throw new IllegalStateException("Entity system not specified");
        } else if (output_folder == null) {
            throw new IllegalStateException("Output folder not specified");
        } else if (source_folder == null) {
            throw new IllegalStateException("Source folder not specified");
        }
        return new FileSource(entitySystem, output_folder, source_folder);
    }

    public FileSourceBuilder setEntitySystem(EntitySystem entitySystem) {
        Objects.requireNonNull(entitySystem, "Entity system cannot be null");
        this.entitySystem = entitySystem;
        return this;
    }

    public FileSourceBuilder setOutputFolder(String pathname) {
        Objects.requireNonNull(pathname, "Output folder path cannot be null");
        
        Path test_path = Path.of(pathname);
        if (!Files.exists(test_path)) {
            throw new IllegalArgumentException("Output folder path does not exist");
        } else if (!Files.isDirectory(test_path)) {
            throw new IllegalArgumentException("Output folder path is not a directory");
        }
        output_folder = test_path;
        return this;
    }

    public FileSourceBuilder setSourceFolder(String pathname) {
        Objects.requireNonNull(pathname, "Source folder path cannot be null");
        
        Path test_path = Path.of(pathname);
        if (!Files.exists(test_path)) {
            throw new IllegalArgumentException("Source folder path does not exist");
        } else if (!Files.isDirectory(test_path)) {
            throw new IllegalArgumentException("Source folder path is not a directory");
        }
        source_folder = test_path;
        return this;
    }
}
