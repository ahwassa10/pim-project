package model.traits;

import java.util.Objects;

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
    
    public static boolean isValidName(String name) {
        Objects.requireNonNull(name, "Name cannot be null");
        return !name.equals("");
    }
    
    public static boolean isValidDescription(String description) {
        Objects.requireNonNull(description, "Description cannot be null");
        return true;
    }
    
    public static String requireValidName(String name) {
        if (!isValidName(name)) {
            String msg = String.format("%s is not a valid name", name);
            throw new IllegalArgumentException(msg);
        }
        return name;
    }
    
    public static String requireValidDescription(String description) {
        if (!isValidDescription(description)) {
            String msg = String.format("%s is not a valid description", description);
            throw new IllegalArgumentException(msg);
        }
        return description;
    }
    
    public String toString() {
        return "Trait<" + this.getName() + ">";
    }
}
