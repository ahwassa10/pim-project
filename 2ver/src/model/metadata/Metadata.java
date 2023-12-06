package model.metadata;

import java.util.UUID;

import model.mappers.Mapper;

public interface Metadata<T> {
    UUID getMetadataID();
    
    Mapper<UUID, T> view();
    Trait<T> getTrait(UUID entity);
    
    UUID attach(UUID entity, T value);
    void detach(UUID entity, T value);
    void detachAll(UUID entity);
}
