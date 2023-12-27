package model.entity;

import java.util.UUID;

public final class Tag {
    private final UUID tagKey;
    private final TagCore tagCore;
    
    public Tag(UUID tagKey, TagCore tagCore) {
        this.tagKey = tagKey;
        this.tagCore = tagCore;
    }
    
    public UUID getTagKey() {
        return tagKey;
    }
    
    public String getTagName() {
        return tagCore.getName();
    }
}
