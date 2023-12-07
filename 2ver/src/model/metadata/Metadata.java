package model.metadata;

import java.util.UUID;

import model.mappers.Mapper;

public interface Metadata {
    UUID getMetadataID();
    
    Mapper<UUID, Boolean> viewAssociations();
    Mapper<UUID, UUID> viewTraits();
    
    Trait asTrait(UUID entityID);
}
