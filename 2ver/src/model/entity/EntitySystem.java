package model.entity;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import model.metadata.Metadatas;
import model.metadata.Metadatas.MarkedMetadata;
import model.metadata.Metadatas.SingleMetadata;
import model.metadata.Trait;
import model.metadata.ValueTrait;

public final class EntitySystem {
    private final MarkedMetadata exis = Metadatas.markedMetadata();
    private final SingleMetadata<String> name = Metadatas.singleMetadata();
    private final SingleMetadata<String> description = Metadatas.singleMetadata();
    
    private UUID create(UUID entityID, String entityName, String entityDescription) {
        UUID exisID = exis.mark(entityID);
        name.attach(exisID, entityName);
        description.attach(exisID, entityDescription);
        
        return exisID;
    }
    
    public UUID create() {
        return create(UUID.randomUUID(), "", "");
    }
    
    public UUID create(String entityName) {
        return create(UUID.randomUUID(), entityName, "");
    }
    
    public UUID create(String entityName, String entityDescription) {
        return create(UUID.randomUUID(), entityName, entityDescription);
    }
    
    private SysEntity buildEntity(UUID exisID) {
        return new SysEntity() {
            private final Trait exisTrait = new Trait() {
                public UUID getTraitID() {
                    return exisID;
                }
            };
            private final ValueTrait<String> nameTrait = name.asValueTrait(exisID);
            private final ValueTrait<String> descriptionTrait = description.asValueTrait(exisID);
            
            public Trait getExisTrait() {
                return exisTrait;
            }
            
            public ValueTrait<String> getNameTrait() {
                return nameTrait;
            }
            
            public ValueTrait<String> getDescriptionTrait() {
                return descriptionTrait;
            }
        };
    }
    
    public Stream<SysEntity> stream() {
        return exis.stream().map(exisID -> buildEntity(exisID));
    }
    
    public SysEntity asEntity(UUID exisID) {
        UUID entityID = exis.computeID(exisID);
        
        if (!exis.isAssociated(entityID)) {
            String msg = String.format("%s is not a system entity", exisID);
            throw new IllegalArgumentException(msg);
        }
        
        return buildEntity(exisID);
    }
    
    public void setName(UUID exisID, String newName) {
        Objects.requireNonNull(newName);
        
        name.detachAll(exisID);
        name.attach(exisID, newName);
    }
    
    public void setDescription(UUID exisID, String newDescription) {
        Objects.requireNonNull(newDescription);
        
        description.detachAll(exisID);
        description.attach(exisID, newDescription);
    }
    
    public void remove(UUID exisID) {
        UUID entityID = exis.computeID(exisID);
        
        if (!exis.isAssociated(entityID)) {
            String msg = String.format("%s is not a system entity", exisID);
            throw new IllegalArgumentException(msg);
        } 
        
        exis.unmark(entityID);
    }
}