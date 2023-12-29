package model.entity;

import java.time.Instant;
import java.util.UUID;

public final class Tag {
    private final UUID tagKey;
    private final ContentCore contentCore;
    
    public Tag(UUID tagKey, ContentCore contentCore) {
        this.tagKey = tagKey;
        this.contentCore = contentCore;
    }
    
    public UUID getTagKey() {
        return tagKey;
    }
    
    public String getName() {
        return contentCore.getName();
    }
    
    public String getDescription() {
        return contentCore.getDescription();
    }
    
    public Instant getDateCreated() {
        return contentCore.getDateCreated();
    }
}
