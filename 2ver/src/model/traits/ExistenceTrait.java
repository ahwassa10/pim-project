package model.traits;

public final class ExistenceTrait implements Trait {
    private final String name = "Existence";
    private final String description = "The root of the trait tree: Existence";
    
    public ExistenceTrait() {}
    
    public Trait getAnchor() {
        return this;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDescription() {
        return this.description;
    }
}
