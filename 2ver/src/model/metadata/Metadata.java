package model.metadata;

import java.util.UUID;

public interface Metadata {
    UUID getMetadataID();
    UUID computeTraitID(UUID entityID);
    
    boolean isAssociated(UUID entityID);
    Trait asTrait(UUID entityID);
}
