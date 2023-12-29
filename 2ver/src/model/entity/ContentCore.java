package model.entity;

import java.time.Instant;

public final class ContentCore {
    private final String name;
    private final String description;
    private final Instant dateCreated;
    
    public ContentCore(String name, String description, Instant dateCreated) {
        this.name = name;
        this.description = description;
        this.dateCreated = dateCreated;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public Instant getDateCreated() {
        return dateCreated;
    }
}
