package substance;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public final class SubstanceStoreBuilder {
    private Path damaged_folder;
    private Path substance_folder;

    public SubstanceStoreBuilder() {
    }

    public static SubstanceStore test_substore() {
        return new SubstanceStoreBuilder()
                .setDamagedFolder("C:\\Users\\Primary\\Desktop\\damaged")
                .setSubstanceFolder("C:\\Users\\Primary\\Desktop\\substance")
                .build();
    }

    public SubstanceStore build() {
        if (damaged_folder == null) {
            throw new IllegalStateException("Damaged folder not specified");
        } else if (substance_folder == null) {
            throw new IllegalStateException("Substance folder not specified");
        }
        return new SubstanceStore(damaged_folder, substance_folder);
    }
    
    public SubstanceStoreBuilder setDamagedFolder(String pathname) {
        Objects.requireNonNull(pathname, "Damaged folder path cannot be null");
        
        Path test_path = Path.of(pathname);
        if (!Files.exists(test_path)) {
            throw new IllegalArgumentException("Damaged folder does not exist");
        } else if (!Files.isDirectory(test_path)) {
            throw new IllegalArgumentException("Damaged folder path is not a directory");
        }
        this.damaged_folder = test_path;
        return this;
    }

    public SubstanceStoreBuilder setSubstanceFolder(String pathname) {
        Objects.requireNonNull(pathname, "Substance folder path cannot be null");
        
        Path test_path = Path.of(pathname);
        if (!Files.exists(test_path)) {
            throw new IllegalArgumentException("Substance folder does not exist");
        } else if (!Files.isDirectory(test_path)) {
            throw new IllegalArgumentException("Substance folder path is not a directory");
        }
        this.substance_folder = test_path;
        return this;
    }
}
