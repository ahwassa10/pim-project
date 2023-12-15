package model.metadata;

import java.util.UUID;
import java.util.stream.Stream;

import model.util.UUIDs;

public interface Metadata {
    UUID getID();
    boolean contains(UUID entityID);
    Stream<UUID> stream();
    
    default UUID computeID(UUID entityID) {
        return UUIDs.xorUUIDs(getID(), entityID);
    }
}
