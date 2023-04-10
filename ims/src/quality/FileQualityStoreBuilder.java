package quality;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public final class FileQualityStoreBuilder {
    private Path export_folder;
    private Path quality_folder;

    public FileQualityStoreBuilder() {
    }

    public static FileQualityStore test_qualitystore() {
        return new FileQualityStoreBuilder()
                .setExportFolder("C:\\Users\\Primary\\Desktop\\export")
                .setQualityFolder("C:\\Users\\Primary\\Desktop\\quality")
                .build();
    }

    public FileQualityStore build() {
        if (quality_folder == null) {
            throw new IllegalStateException("Quality folder not specified");
        } else if (export_folder == null) {
            throw new IllegalStateException("Output folder not specified");
        }
        return new FileQualityStore(export_folder, quality_folder);
    }

    public FileQualityStoreBuilder setExportFolder(String pathname) {
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

    public FileQualityStoreBuilder setQualityFolder(String pathname) {
        Objects.requireNonNull(pathname, "Quality folder path cannot be null");
        
        Path test_path = Path.of(pathname);
        if (!Files.exists(test_path)) {
            throw new IllegalArgumentException("Quality folder does not exist");
        } else if (!Files.isDirectory(test_path)) {
            throw new IllegalArgumentException("Storage folder path is not a directory");
        }
        this.quality_folder = test_path;
        return this;
    }
}
