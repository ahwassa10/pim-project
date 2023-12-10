package model.metadata;

import java.util.UUID;

import model.mappers.Mapper;

public abstract class AbstractMetadata implements Metadata {
    private final UUID metadataID = UUID.randomUUID();
    
    private final Mapper<UUID, Boolean> associationMapper;
    
    public AbstractMetadata(Mapper<UUID, Boolean> associationMapper) {
        this.associationMapper = associationMapper;
    }
    
    public UUID getMetadataID() {
        return metadataID;
    }
    
    public Mapper<UUID, Boolean> viewAssociations() {
        return associationMapper;
    }
    
}
