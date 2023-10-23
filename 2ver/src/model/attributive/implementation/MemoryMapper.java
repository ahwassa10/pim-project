package model.attributive.implementation;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import model.attributive.specification.Mapper;
import model.attributive.specification.View;

public class MemoryMapper<T, U> implements Mapper<T, U> {
    private final Map<T, Set<U>> propertiesMap = new HashMap<>();
    
    private final Map<U, Set<T>> attributesMap = new HashMap<>();
    
    private static final class BasicView<X, Y> implements View<X, Y> {
        private final Map<X, Set<Y>> map;
        
        public BasicView(Map<X, Set<Y>> map) {
            this.map = map;
        }
        
        public boolean hasMappings(X object) {
            return map.containsKey(object);
        }
        
        public Set<Y> getMappings(X object) {
            if (!map.containsKey(object)) {
                String msg = String.format("%s is not mapped to any object", object);
                throw new IllegalArgumentException(msg);
            }
            return Collections.unmodifiableSet(map.get(object));
        }
        
        public Iterator<Y> iterateMappings(X object) {
            if (map.containsKey(object)) {
                return Collections.unmodifiableSet(map.get(object)).iterator();
            } else {
                return Collections.emptyIterator();
            }
        }
    }
    
    public View<T, U> attributions() {
        return new BasicView<T, U>(propertiesMap);
    }
    
    public View<U, T> propertizations() {
        return new BasicView<U, T>(attributesMap);
    }
    
    public void map(T attributer, U propertizer) {
        Mappers.requireNoMapping(this, attributer, propertizer);
        
        propertiesMap.computeIfAbsent(attributer, a -> new HashSet<>()).add(propertizer);
        attributesMap.computeIfAbsent(propertizer, h -> new HashSet<>()).add(attributer);
    }
    
    private void removeProperty(U propertizer, T attributer) {
        Set<U> properties = propertiesMap.get(attributer);
        
        properties.remove(propertizer);
        if (properties.size() == 0) {
            propertiesMap.remove(attributer);
        }        
    }
    
    private void removeAttribute(T attributer, U propertizer) {
        Set<T> attributes = attributesMap.get(propertizer);
        
        attributes.remove(attributer);
        if(attributes.size() == 0) {
            attributesMap.remove(propertizer);
        }
    }
    
    public void unmap(T attributer, U propertizer) {
        Mappers.requireMapping(this, attributer, propertizer);
        
        removeProperty(propertizer, attributer);
        removeAttribute(attributer, propertizer);
    }
    
    public void delete(T attributer) {
        Mappers.requireAttributions(this, attributer);
        
        // To delete an attributer, we remove all its properties.
        Set<U> propertizers = propertiesMap.remove(attributer);
        
        // Then we have to remove all the references from the propertizers to the attributer.
        for(U propertizer : propertizers) {
            removeAttribute(attributer, propertizer);
        }
    }
    
    public void forget(U propertizer) {
        Mappers.requirePropertizations(this, propertizer);
        
        // To forget about a propertizer, we remove all its attributes.
        Set<T> attributes = attributesMap.remove(propertizer);
        
        // Then we have to remove all the references from the attributers to the propertizer.
        for (T attribute : attributes) {
            removeProperty(propertizer, attribute);
        }
    }
}