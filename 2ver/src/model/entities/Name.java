package model.entities;

import java.util.UUID;

import model.mappers.implementation.MemMappers;
import model.mappers.implementation.MemMappers.MultiMapper;

public final class Name {
    private final MultiMapper<String, UUID> mapper;
    
    public Name() {
        mapper = MemMappers.multiMapper();
    }
    
    public void attach(UUID content, String name) {
        mapper.map(name, content);
    }
    
    public void detach(UUID content, String name) {
        mapper.unmap(name, content);
    }
    
    
    
    
}
