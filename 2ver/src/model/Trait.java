package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public interface Trait {
    static boolean isValidDescription(String description) {
        Objects.requireNonNull(description, "Description cannot be null");
        return true;
    }
    
    static boolean isValidName(String name) {
        Objects.requireNonNull(name, "Name cannot be null");
        return !name.equals("");
    }
    
    static String requireValidDescription(String description) {
        if (!isValidDescription(description)) {
            String msg = String.format("%s is not a valid description", description);
            throw new IllegalArgumentException(msg);
        }
        return description;
    }
    
    static String requireValidName(String name) {
        if (!isValidName(name)) {
            String msg = String.format("%s is not a valid name", name);
            throw new IllegalArgumentException(msg);
        }
        return name;
    }
    
    String getDescription();
    String getName();
    
    default Trait getAnchorTrait() {
        Trait trait = this;
        while (trait != trait.getSuperTrait()) {
            trait = trait.getSuperTrait();
        }
        return trait;
    }
    
    Trait getSuperTrait();
    
    default List<Trait> getSuperTraits() {
        List<Trait> superTraits = new ArrayList<>();
        
        Trait trait = this;
        while (trait != trait.getSuperTrait()) {
            trait = trait.getSuperTrait();
            superTraits.add(trait);
        }
        
        Collections.reverse(superTraits);
        return superTraits;
    }
}
