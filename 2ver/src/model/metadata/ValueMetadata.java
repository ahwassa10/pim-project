package model.metadata;

import java.util.UUID;

import model.mapper.Mapper;

public interface ValueMetadata<T> extends Metadata {
    Mapper<UUID, T> viewValues();
    
    ValueTrait<T> asValueTrait(UUID entityID);
}