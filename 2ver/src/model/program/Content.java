package model.program;

import java.util.UUID;

public final class Content {
    private final UUID contentKey;
    private final ContentCore contentCore;
    
    public Content(UUID contentKey, ContentCore contentCore) {
        this.contentKey = contentKey;
        this.contentCore = contentCore;
    }
    
    public UUID getContentKey() {
        return contentKey;
    }
    
    public String getName() {
        return contentCore.getName();
    }
    
    public String getDescription() {
        return contentCore.getDescription();
    }
}
