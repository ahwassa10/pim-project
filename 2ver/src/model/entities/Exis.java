package model.entities;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import model.mappers.implementation.MemMappers;
import model.mappers.implementation.MemMappers.MultiMapper;
import model.mappers.specification.Mapper;

public final class Exis {
    private final MultiMapper<UUID, Object> mapping;
    
    public Exis() {
        mapping = MemMappers.multiMapper();
    }
    
    public void attach(UUID content, Object metadata) {
        mapping.map(content, metadata);
    }
    
    public void detach(UUID content, Object metadata) {
        if (Objects.equals(this, metadata)) {
            String msg = "It is impossible to remove the Exis trait";
            throw new IllegalArgumentException(msg);
        } else {
            mapping.unmap(content, metadata);
        }
    }
    
    public Mapper<UUID, Object> view() {
        return new Mapper<UUID, Object>() {
            public boolean hasMapping(UUID content, Object metadata) {
                return metadata == Exis.this ? true : mapping.hasMapping(content, metadata);
            }
            
            public int countValues(UUID content) {
                return 1 + mapping.countValues(content);
            }
            
            public Iterator<Object> iterateValues(UUID content) {
                return new Iterator<Object>() {
                    private Iterator<Object> otherMetadata = mapping.iterateValues(content);
                    private boolean iteratedFirst = false;
                    
                    public boolean hasNext() {
                        return !iteratedFirst || otherMetadata.hasNext();
                    }
                    
                    public Object next() {
                        if (!iteratedFirst) {
                            iteratedFirst = true;
                            return Exis.this;
                        } else {
                            return otherMetadata.next();
                        }
                    }
                };
            }
            
            public boolean hasValues(UUID content) {
                return true;
            }
            
            public Object anyValue(UUID content) {
                return Exis.this;
            }
            
            public Set<Object> getValues(UUID content) {
                Set<Object> metadata = new HashSet<>();
                metadata.add(this);
                
                if (mapping.hasValues(content)) {
                    metadata.addAll(mapping.getValues(content));
                }
                
                return metadata;
            }
        };
    }
}
