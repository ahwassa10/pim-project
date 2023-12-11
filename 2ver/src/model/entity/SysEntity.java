package model.entity;

import java.util.UUID;

import model.metadata.Trait;
import model.metadata.ValueTrait;

public interface SysEntity {
    default UUID getExisID() {
        return getExisTrait().getTraitID();
    }
    
    default String getName() {
        return getNameTrait().anyValue();
    }
    
    default String getDescription() {
        return getDescriptionTrait().anyValue();
    }
    
    Trait getExisTrait();
    ValueTrait<String> getNameTrait();
    ValueTrait<String> getDescriptionTrait();
}
