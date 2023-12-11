package model.metadata;

import java.util.UUID;

public interface Metadata {
    UUID getMetadataID();
    UUID computeID(UUID entityID);
    
    boolean isAssociated(UUID entityID);
    Trait asTrait(UUID entityID);
}
