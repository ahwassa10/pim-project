package model.entity;

import java.util.Objects;
import java.util.UUID;

import model.metadata.Metadatas;
import model.metadata.Metadatas.SingleMetadata;

public final class ContentSystem {
    public static class Content {
        private final String name;
        private final String description;
        
        public Content(String name, String description) {
            this.name = name;
            this.description = description;
        }
        
        public String getName() {
            return name;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    private final SingleMetadata<Content> contentMetadata = Metadatas.singleMetadata();
    
    public UUID create(UUID rawID, String name, String description) {
        Content content = new Content(name, description);
        UUID contentID = contentMetadata.attach(rawID, content);
        
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
    
    public void setName(UUID contentID, String newName) {
        Objects.requireNonNull(newName);
        
        UUID rawID = contentMetadata.computeID(contentID);
        
        String description = contentMetadata.viewValues().get(rawID).certainly().any().getDescription();
        contentMetadata.detachAll(rawID);
        contentMetadata.attach(rawID, new Content(newName, description));
    }
    
    public void setDescription(UUID contentID, String newDescription) {
        Objects.requireNonNull(newDescription);
        
        UUID rawID = contentMetadata.computeID(contentID);
        
        String name = contentMetadata.viewValues().get(rawID).certainly().any().getName();
        contentMetadata.detachAll(rawID);
        contentMetadata.attach(rawID, new Content(name, newDescription));
    }
    
    public void remove(UUID contentID) {
        UUID rawID = contentMetadata.computeID(contentID);
        
        if (!contentMetadata.viewValues().get(rawID).has()) {
            String msg = String.format("%s is not a system entity", contentID);
            throw new IllegalArgumentException(msg);
        }
        
        contentMetadata.detachAll(rawID);
    }
}