package model.traits;

public abstract class AbstractTrait implements Trait {
    private final String name;
    private final String description;
    
    public AbstractTrait(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String toString() {
        return "Trait<" + this.getName() + ">";
    }
}
