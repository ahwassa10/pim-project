package model.entity;

import java.nio.file.Path;
import java.util.UUID;

public interface ImageEntity {
    UUID getImageID();
    Path getSource();
}
