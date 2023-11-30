package model.entities.metadata;

import java.util.UUID;

import model.mappers.specification.Mapper;

public interface Metadata<T> {
    public UUID getMetadataID();
    
    public Mapper<UUID, T> view();
    
    public UUID attach(UUID entity, T value);
    public void detach(UUID entity, T value);
    public void detachAll(UUID entity);
}
