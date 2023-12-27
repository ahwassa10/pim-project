package model.program;

public final class ContentCore {
    private final String name;
    private final String description;
    
    public ContentCore(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
}
