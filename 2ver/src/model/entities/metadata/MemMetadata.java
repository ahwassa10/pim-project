package model.entities.metadata;

import java.util.UUID;

import model.entities.UUIDs;
import model.mappers.implementation.MemMappers;
import model.mappers.specification.Mapper;
import model.mappers.specification.MutableMapper;

public class MemMetadata<T> implements Metadata<T> {
    private final UUID metadataID;
    
    private final MutableMapper<UUID, T> mapper;
    
    public MemMetadata() {
        this.metadataID = UUID.randomUUID();
        this.mapper = MemMappers.multiMapper();
    }
    
    public UUID metadataID() {
        return metadataID;
    }
    
    public Mapper<UUID, T> view() {
        return mapper;
    }
    
    public UUID attach(UUID entity, T value) {
        mapper.map(entity, value);
        
        return UUIDs.xorUUIDs(entity, metadataID);
    }
    
    public void detach(UUID entity, T value) {
        mapper.unmap(entity, value);
    }
    
    public void detachAll(UUID entity) {
        mapper.unmapAll(entity);
    }
}
