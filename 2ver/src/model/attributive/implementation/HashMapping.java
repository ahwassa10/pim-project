package model.attributive.implementation;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import model.attributive.specification.Mapping;
import model.attributive.specification.View;

public class HashMapping<T, U> implements Mapping<T, U> {
    private final Map<T, Set<U>> propertiesMap = new HashMap<>();
    
    private final Map<U, Set<T>> attributesMap = new HashMap<>();
    
    
    public View<T, U> attributions() {
        return new View<T, U>() {
            public boolean hasMappings(T attributer) {
                return propertiesMap.containsKey(attributer);
            }
            
            public Set<U> getMappings(T attributer) {
                return Collections.unmodifiableSet(propertiesMap.get(attributer));
            }
            
            public Iterator<U> iterateMappings(T attributer) {
                if (propertiesMap.containsKey(attributer)) {
                    return propertiesMap.get(attributer).iterator();
                } else {
                    return Collections.emptyIterator();
                }
            }
        };
    }
    
    public View<U, T> propertizations() {
        return new View<U, T>() {
            public boolean hasMappings(U propertizer) {
                return attributesMap.containsKey(propertizer);
            }
            
            public Set<T> getMappings(U propertizer) {
                return Collections.unmodifiableSet(attributesMap.get(propertizer));
            }
            
            public Iterator<T> iterateMappings(U propertizer) {
                if (attributesMap.containsKey(propertizer)) {
                    return attributesMap.get(propertizer).iterator();
                } else {
                    return Collections.emptyIterator();
                }
            }
        };
    }
    
    public void apply(T attributer, U propertizer) {
        Mappings.requireNoMapping(this, attributer, propertizer);
        
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
    
    public void remove(T attributer, U propertizer) {
        Mappings.requireMapping(this, attributer, propertizer);
        
        removeProperty(propertizer, attributer);
        removeAttribute(attributer, propertizer);
    }
    
    public void delete(T attributer) {
        Mappings.requireAttributions(this, attributer);
        
        // To delete an attributer, we remove all its properties.
        Set<U> propertizers = propertiesMap.remove(attributer);
        
        // Then we have to remove all the references from the propertizers to the attributer.
        for(U propertizer : propertizers) {
            removeAttribute(attributer, propertizer);
        }
    }
    
    public void forget(U propertizer) {
        Mappings.requirePropertizations(this, propertizer);
        
        // To forget about a propertizer, we remove all its attributes.
        Set<T> attributes = attributesMap.remove(propertizer);
        
        // Then we have to remove all the references from the attributers to the propertizer.
        for (T attribute : attributes) {
            removeProperty(propertizer, attribute);
        }
    }
}