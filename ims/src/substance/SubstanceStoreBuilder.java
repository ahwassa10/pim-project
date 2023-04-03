package substance;

import java.nio.file.Files;
import java.nio.file.Path;

public final class SubstanceStoreBuilder {
    private Path substance_folder;

    public SubstanceStoreBuilder() {
    }

    public static SubstanceStore test_substore() {
        return new SubstanceStoreBuilder()
                .setSubstanceFolder("C:\\Users\\Primary\\Desktop\\substance").build();
    }

    public SubstanceStore build() {
        if (substance_folder == null) {
            throw new IllegalStateException("Substance folder not specified");
        }
        return new SubstanceStore(substance_folder);
    }

    public SubstanceStoreBuilder setSubstanceFolder(String pathname) {
        if (pathname == null) {
            throw new IllegalArgumentException("Substance folder path cannot be null");
        }
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
