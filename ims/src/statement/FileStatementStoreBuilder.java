package statement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public final class FileStatementStoreBuilder {
    private Path export_folder;
    private Path statement_folder;

    public FileStatementStoreBuilder() {}

    public static FileStatementStore test_statementstore() {
        return new FileStatementStoreBuilder()
                .setExportFolder("C:\\Users\\Primary\\Desktop\\export")
                .setStatementFolder("C:\\Users\\Primary\\Desktop\\statement")
                .build();
    }

    public FileStatementStore build() {
        if (statement_folder == null) {
            throw new IllegalStateException("Statement folder not specified");
        } else if (export_folder == null) {
            throw new IllegalStateException("Output folder not specified");
        }
        
        try {
            FileStatementStore f = new FileStatementStore(export_folder, statement_folder);
            String message = "Successfully created a file-based statement store";
            System.out.println(message);
            return f;
        } catch (IOException e) {
            String message = "Failed to create a file-based statement store";
            System.out.println(message);
            return null;
        }
    }

    public FileStatementStoreBuilder setExportFolder(String pathname) {
        Objects.requireNonNull(pathname, "Export folder path cannot be null");
        
        Path test_path = Path.of(pathname);
        if (!Files.exists(test_path)) {
            throw new IllegalArgumentException("Export folder does not exist");
        } else if (!Files.isDirectory(test_path)) {
            throw new IllegalArgumentException("Export folder path is not a directory");
        }
        this.export_folder = test_path;
        return this;
    }

    public FileStatementStoreBuilder setStatementFolder(String pathname) {
        Objects.requireNonNull(pathname, "Statement folder path cannot be null");
        
        Path test_path = Path.of(pathname);
        if (!Files.exists(test_path)) {
            throw new IllegalArgumentException("Statement folder does not exist");
        } else if (!Files.isDirectory(test_path)) {
            throw new IllegalArgumentException("Statement folder path is not a directory");
        }
        this.statement_folder = test_path;
        return this;
    }
}
