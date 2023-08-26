package program;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class Model {
    private Path directoryPath;
    
    public Path getDirectoryPath() {
        return directoryPath;
    }
    
    public void SetDirectoryPath(Path directoryPath) {
        Objects.requireNonNull(directoryPath, "Directory path cannot be null");
        
        if (!Files.isDirectory(directoryPath)) {
            String msg = String.format("%s is not a valid directory", directoryPath);
            throw new IllegalArgumentException(msg);
        }
        
        this.directoryPath = directoryPath;
    }
}
