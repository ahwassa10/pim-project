package model.entities.metadata;

import java.util.UUID;

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
    
    public static UUID xorUUIDs(UUID uuid1, UUID uuid2) {
        long msb1 = uuid1.getMostSignificantBits();
        long lsb1 = uuid1.getLeastSignificantBits();

        long msb2 = uuid2.getMostSignificantBits();
        long lsb2 = uuid2.getLeastSignificantBits();

        long xorMsb = msb1 ^ msb2;
        long xorLsb = lsb1 ^ lsb2;

        return new UUID(xorMsb, xorLsb);
    }
    
    public UUID attach(UUID entity, T value) {
        mapper.map(entity, value);
        
        return xorUUIDs(entity, metadataID);
    }
    
    public void detach(UUID entity, T value) {
        mapper.unmap(entity, value);
    }
    
    public void detachAll(UUID entity) {
        mapper.unmapAll(entity);
    }
}
