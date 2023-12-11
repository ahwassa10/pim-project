package model.entity;

import java.nio.file.Path;
import java.util.UUID;

import model.metadata.Trait;
import model.metadata.ValueTrait;

public interface ImageEntity {
    default UUID getImageID() {
        return getImageTrait().getTraitID();
    }
    
    default Path getSource() {
        return getSourceTrait().anyValue();
    }
    
    
    Trait getImageTrait();
    ValueTrait<Path> getSourceTrait();
}
