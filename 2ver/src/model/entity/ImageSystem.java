package model.entity;

import java.nio.file.Path;
import java.util.UUID;
import java.util.stream.Stream;

import model.metadata.Metadatas;
import model.metadata.Metadatas.Association;
import model.metadata.Metadatas.SingleTable;

public final class ImageSystem {
    private final Association presence = Metadatas.markedMetadata();
    private final SingleTable<Path> sourceMetadata = Metadatas.singleMetadata();
    
    public UUID create(UUID rawID, Path imageSource) {
        UUID imageID = presence.associate(rawID);
        sourceMetadata.attach(imageID, imageSource);
        
        return imageID;
    }
    
    private ImageEntity buildEntity(UUID imageID) {
        return new ImageEntity() {
            private final UUID id = imageID;
            private final Path source = sourceMetadata.view().get(imageID).certainly().any();
            
            public UUID getImageID() {
                return id;
            }
            
            public Path getSource() {
                return source;
            }
        };
    }
    
    public Stream<ImageEntity> stream() {
        return presence.stream().map(imageID -> buildEntity(imageID));
    }
    
    public ImageEntity asEntity(UUID imageID) {
        UUID rawID = presence.computeID(imageID);
        
        if (!presence.contains(rawID)) {
            String msg = String.format("%s is not an image entity", imageID);
            throw new IllegalArgumentException(msg);
        }
        
        return buildEntity(imageID);
    }
    
    public void remove(UUID imageID) {
        UUID rawID = presence.computeID(imageID);
        
        if (!presence.contains(rawID)) {
            String msg = String.format("%s is not an image entity", imageID);
            throw new IllegalArgumentException(msg);
        }
        
        presence.unmark(rawID);
    }
}
