package model.metadata;

import java.util.UUID;
import java.util.stream.Stream;

public interface Metadata {
    UUID getMetadataID();
    UUID computeID(UUID entityID);
    
    Stream<UUID> stream();
    
    boolean isAssociated(UUID entityID);
    Trait asTrait(UUID entityID);
}
