package model.entity;

import java.nio.file.Path;
import java.util.UUID;
import java.util.stream.Stream;

import model.metadata.Metadatas;
import model.metadata.Metadatas.MarkedMetadata;
import model.metadata.Metadatas.SingleMetadata;
import model.metadata.Trait;
import model.metadata.ValueTrait;

public final class ImageSystem {
    private final MarkedMetadata presence = Metadatas.markedMetadata();
    private final SingleMetadata<Path> source = Metadatas.singleMetadata();
    
    public UUID create(UUID contentID, Path imageSource) {
        UUID imageID = presence.mark(contentID);
        source.attach(imageID, imageSource);
        
        return imageID;
    }
    
    private ImageEntity buildEntity(UUID imageID) {
        return new ImageEntity() {
            private final Trait imageTrait = new Trait() {
                public UUID getTraitID() {
                    return imageID;
                }
            };
            
            private final ValueTrait<Path> sourceTrait = source.asValueTrait(imageID);
            
            public Trait getImageTrait() {
                return imageTrait;
            }
            
            public ValueTrait<Path> getSourceTrait() {
                return sourceTrait;
            }
        };
    }
    
    public Stream<ImageEntity> stream() {
        return presence.stream().map(imageID -> buildEntity(imageID));
    }
    
    public ImageEntity asEntity(UUID imageID) {
        UUID contentID = presence.computeID(imageID);
        
        if (!presence.isAssociated(contentID)) {
            String msg = String.format("%s is not an image entity", imageID);
            throw new IllegalArgumentException(msg);
        }
        
        return buildEntity(imageID);
    }
    
    public void remove(UUID imageID) {
        UUID contentID = presence.computeID(imageID);
        
        if (!presence.isAssociated(contentID)) {
            String msg = String.format("%s is not an image entity", imageID);
            throw new IllegalArgumentException(msg);
        }
        
        presence.unmark(contentID);
    }
}
