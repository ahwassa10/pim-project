package model.entity;

import java.util.Objects;
import java.util.UUID;

import model.metadata.Metadatas;
import model.metadata.Metadatas.MarkedMetadata;
import model.metadata.Metadatas.SingleMetadata;
import model.metadata.Trait;
import model.metadata.ValueTrait;

public final class EntitySystem {
    private final MarkedMetadata exis = Metadatas.markedMetadata();
    private final SingleMetadata<String> name = Metadatas.singleMetadata();
    private final SingleMetadata<String> description = Metadatas.singleMetadata();
    
    public UUID create() {
        UUID entityID = UUID.randomUUID();
        UUID exisID = exis.mark(entityID);
        
        String blankName = "";
        name.attach(exisID, blankName);
        
        String blankDescription = "";
        description.attach(exisID, blankDescription);
        
        return exisID;
    }
    
    public SysEntity get(UUID exisID) {
        UUID entityID = exis.computeTraitID(exisID);
        
        if (!exis.isAssociated(entityID)) {
            String msg = String.format("%s is not a system entity", exisID);
            throw new IllegalArgumentException(msg);
        }
        
        return new SysEntity() {
            private final Trait exisTrait = exis.asTrait(entityID);
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
        UUID entityID = exis.computeTraitID(exisID);
        
        if (!exis.isAssociated(entityID)) {
            String msg = String.format("%s is not a system entity", exisID);
            throw new IllegalArgumentException(msg);
        } 
        
        exis.unmark(entityID);
    }
}