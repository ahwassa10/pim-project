package model.attributive.implementation;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import model.attributive.specification.DenseMap;

public final class HashDenseMap<T, U> implements DenseMap<T, U> {
    private final Map<T, Set<U>> properties = new HashMap<>();
    
    private final Map<U, Set<T>> attributes = new HashMap<>();
    
    public boolean hasProperties(T attributer) {
        return properties.containsKey(attributer);
    }
    
    public Set<U> getProperties(T attributer) {
        DenseMaps.requireProperties(this, attributer);
        return Collections.unmodifiableSet(properties.get(attributer));
    }
    
    public Iterator<U> iterateProperties(T attributer) {
        if (properties.containsKey(attributer)) {
            return properties.get(attributer).iterator();
        } else {
            return Collections.emptyIterator();
        }
    }
    
    public boolean hasAttributes(U holder) {
        return attributes.containsKey(holder);
    }
    
    public Set<T> getAttributes(U holder) {
        DenseMaps.requireAttributes(this, holder);
        return Collections.unmodifiableSet(attributes.get(holder));
    }
    
    public Iterator<T> iterateAttributes(U holder) {
        if (attributes.containsKey(holder)) {
            return attributes.get(holder).iterator();
        } else {
            return Collections.emptyIterator();
        }
    }
    
    public void apply(T attributer, U holder) {
        properties.computeIfAbsent(attributer, a -> new HashSet<>()).add(holder);
        attributes.computeIfAbsent(holder, h -> new HashSet<>()).add(attributer);
    }
    
    private void removeAttribution(T attributer, U holder) {
        Set<U> attributerProperties = properties.get(attributer);
        attributerProperties.remove(holder);
        if (attributerProperties.size() == 0) {
            properties.remove(attributer);
        }
    }
    
    private void forgetPropertization(T attributer, U holder) {
        Set<T> holderAttributes = attributes.get(holder);
        holderAttributes.remove(attributer);
        if (holderAttributes.size() == 0) {
            attributes.remove(holder);
        }
    }
    
    public void remove(T attributer, U holder) {
        DenseMaps.requireProperties(this, attributer);
        DenseMaps.requireAttributes(this, holder);
        
        removeAttribution(attributer, holder);
        forgetPropertization(attributer, holder);
    }
    
    public void remove(T attributer) {
        DenseMaps.requireProperties(this, attributer);
        
        Set<U> holders = properties.get(attributer);
        for (U holder : holders) {
            forgetPropertization(attributer, holder);
        }
        
        properties.remove(attributer);
    }
    
    public void forget(U holder) {
        DenseMaps.requireAttributes(this, holder);
        
        Set<T> attributers = attributes.get(holder);
        for (T attributer : attributers) {
            removeAttribution(attributer, holder);
        }
        
        attributes.remove(holder);
    }
    
}
