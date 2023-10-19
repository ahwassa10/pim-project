package model.attributive.implementation;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import model.attributive.specification.DenseMap;

public final class HashDenseMap<T, U> implements DenseMap<T, U> {
    private final Map<T, Set<U>> attributions = new HashMap<>();
    
    private final Map<U, Set<T>> attributes = new HashMap<>();
    
    public boolean hasAttributions(T attributer) {
        return attributions.containsKey(attributer);
    }
    
    public Set<U> getAttributions(T attributer) {
        BiMaps.requireAttributions(this, attributer);
        return Collections.unmodifiableSet(attributions.get(attributer));
    }
    
    public Iterator<U> iterateAttributions(T attributer) {
        if (attributions.containsKey(attributer)) {
            return attributions.get(attributer).iterator();
        } else {
            return Collections.emptyIterator();
        }
    }
    
    public boolean hasAttributes(U object) {
        return attributes.containsKey(object);
    }
    
    public Set<T> getAttributes(U object) {
        BiMaps.requireAttributes(this, object);
        return Collections.unmodifiableSet(attributes.get(object));
    }
    
    public Iterator<T> iterateAttributes(U object) {
        if (attributes.containsKey(object)) {
            return attributes.get(object).iterator();
        } else {
            return Collections.emptyIterator();
        }
    }
    
    public void apply(T attributer, U object) {
        attributions.computeIfAbsent(attributer, a -> new HashSet<>()).add(object);
        attributes.computeIfAbsent(object, h -> new HashSet<>()).add(attributer);
    }
    
    private void removeAttribution(T attributer, U object) {
        Set<U> attributeHolders = attributions.get(attributer);
        attributeHolders.remove(object);
        if (attributeHolders.size() == 0) {
            attributions.remove(attributer);
        }
    }
    
    private void forgetAttribute(T attributer, U object) {
        Set<T> attributers = attributes.get(object);
        attributers.remove(attributer);
        if (attributers.size() == 0) {
            attributes.remove(object);
        }
    }
    
    public void remove(T attributer, U object) {
        BiMaps.requireAttributions(this, attributer);
        BiMaps.requireAttributes(this, object);
        
        removeAttribution(attributer, object);
        forgetAttribute(attributer, object);
    }
    
    public void remove(T attributer) {
        BiMaps.requireAttributions(this, attributer);
        
        Set<U> objects = attributions.get(attributer);
        for (U object : objects) {
            forgetAttribute(attributer, object);
        }
        
        attributions.remove(attributer);
    }
    
    public void forget(U object) {
        BiMaps.requireAttributes(this, object);
        
        Set<T> attributers = attributes.get(object);
        for (T attributer : attributers) {
            removeAttribution(attributer, object);
        }
        
        attributes.remove(object);
    }
    
}
