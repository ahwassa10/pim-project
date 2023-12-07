package model.metadata;

import java.util.UUID;

import model.mappers.Mapper;

public interface ValueMetadata<T> extends Metadata {
    Mapper<UUID, T> viewValues();
    ValueTrait<T> asValueTrait(UUID entityID);
    
    UUID attach(UUID entityID, T value);
    void detach(UUID entityID, T value);
    void detachAll(UUID entityID);
}
