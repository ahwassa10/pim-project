package model.entity;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import model.metadata.Metadatas;
import model.metadata.Metadatas.MarkedMetadata;
import model.metadata.Metadatas.SingleMetadata;

public final class ContentSystem {
    private final MarkedMetadata presence = Metadatas.markedMetadata();
    private final SingleMetadata<String> nameMetadata = Metadatas.singleMetadata();
    private final SingleMetadata<String> descriptionMetadata = Metadatas.singleMetadata();
    
    private UUID create(UUID rawID, String name, String description) {
        UUID contentID = presence.mark(rawID);
        nameMetadata.attach(contentID, name);
        descriptionMetadata.attach(contentID, description);
        
        return contentID;
    }
    
    public UUID create() {
        return create(UUID.randomUUID(), "", "");
    }
    
    public UUID create(String name) {
        return create(UUID.randomUUID(), name, "");
    }
    
    public UUID create(String name, String description) {
        return create(UUID.randomUUID(), name, description);
    }
    
    private ContentEntity buildEntity(UUID contentID) {
        return new ContentEntity() {
            private final UUID id = contentID;
            private final String name = nameMetadata.viewValues().get(contentID).any();
            private final String description = descriptionMetadata.viewValues().get(contentID).any();
            
            public UUID getContentID() {
                return id;
            }
            
            public String getName() {
                return name;
            }
            
            public String getDescription() {
                return description;
            }
        };
    }
    
    public Stream<ContentEntity> stream() {
        return presence.stream().map(contentID -> buildEntity(contentID));
    }
    
    public ContentEntity asEntity(UUID contentID) {
        UUID rawID = presence.computeID(contentID);
        
        if (!presence.isAssociated(rawID)) {
            String msg = String.format("%s is not a content entity", contentID);
            throw new IllegalArgumentException(msg);
        }
        
        return buildEntity(contentID);
    }
    
    public void setName(UUID contentID, String newName) {
        Objects.requireNonNull(newName);
        
        nameMetadata.detachAll(contentID);
        nameMetadata.attach(contentID, newName);
    }
    
    public void setDescription(UUID contentID, String newDescription) {
        Objects.requireNonNull(newDescription);
        
        descriptionMetadata.detachAll(contentID);
        descriptionMetadata.attach(contentID, newDescription);
    }
    
    public void remove(UUID contentID) {
        UUID rawID = presence.computeID(contentID);
        
        if (!presence.isAssociated(rawID)) {
            String msg = String.format("%s is not a system entity", contentID);
            throw new IllegalArgumentException(msg);
        } 
        
        presence.unmark(rawID);
    }
}