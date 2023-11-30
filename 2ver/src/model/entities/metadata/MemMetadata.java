package model.entities.metadata;

import java.util.UUID;

import model.entities.UUIDs;
import model.mappers.implementation.MemMappers;
import model.mappers.specification.Mapper;
import model.mappers.specification.MutableMapper;

public final class MemMetadata {
    private static abstract class AbstractMetadata<T> implements Metadata<T> {
        private final UUID metadataID;
        
        private final MutableMapper<UUID, T> mapper;
        
        private AbstractMetadata(UUID uuid, MutableMapper<UUID, T> mapper) {
            this.metadataID = uuid;
            this.mapper = mapper;
        }
        
        public UUID getMetadataID() {
            return metadataID;
        }
        
        public Mapper<UUID, T> view() {
            return mapper;
        }
        
        public UUID attach(UUID entity, T value) {
            mapper.map(entity, value);
            
            return UUIDs.xorUUIDs(metadataID, entity);
        }
        
        public void detach(UUID entity, T value) {
            mapper.unmap(entity, value);
        }
        
        public void detachAll(UUID entity) {
            mapper.unmapAll(entity);
        }
    }
    
    public static class SingleMetadata<T> extends AbstractMetadata<T> {
        private SingleMetadata() {
            super(UUID.randomUUID(), MemMappers.singleMapper());
        }
    }
    
    public static class MultiMetadata<T> extends AbstractMetadata<T> {
        private MultiMetadata() {
            super(UUID.randomUUID(), MemMappers.multiMapper());
        }
    }
    
    public static <T> SingleMetadata<T> singleMetadata() {
        return new SingleMetadata<T>();
    }
    
    public static <T> MultiMetadata<T> multiMetadata() {
        return new MultiMetadata<T>();
    }
}