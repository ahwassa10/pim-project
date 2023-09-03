package model;

import java.util.Objects;

public class NaturalTrait implements Trait {
    private final String description;
    private final String name;
    private final Trait superTrait;
    
    NaturalTrait(String name, String description, Trait superTrait) {
        this.name = Trait.requireValidName(name);
        this.description = Trait.requireValidDescription(description);
        this.superTrait = Objects.requireNonNull(superTrait, "SuperTrait cannot be null");
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Trait getSuperTrait() {
        return this.superTrait;
    }
    
    public String toString() {
        return "Trait<" + name + ">";
    }
}
