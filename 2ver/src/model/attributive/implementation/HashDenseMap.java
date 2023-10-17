package model.attributive.implementation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import model.attributive.specification.DenseMap;

public class HashDenseMap<T, U> implements DenseMap<T, U> {
    private final Map<T, Set<U>> attributions = new HashMap<>();
    
    private final Map<U, Set<T>> properties = new HashMap<>();
    
    public boolean hasAttributions(T attributer) {
        return attributions.containsKey(attributer);
    }
    
    public Set<U> getAttributions(T attributer) {
        DenseMaps.requireAttributions(this, attributer);
        return attributions.get(attributer);
    }
    
    public Iterator<U> iterateAttributions(T attributer) {
        return getAttributions(attributer).iterator();
    }
    
    public boolean hasProperties(U holder) {
        return properties.containsKey(holder);
    }
    
    public Set<T> getProperties(U holder) {
        DenseMaps.requireProperties(this, holder);
        return properties.get(holder);
    }
    
    public Iterator<T> iterateProperties(U holder) {
        return getProperties(holder).iterator();
    }
    
    public void attribute(T attributer, U holder) {
        attributions.computeIfAbsent(attributer, a -> new HashSet<>()).add(holder);
        properties.computeIfAbsent(holder, h -> new HashSet<>()).add(attributer);
    }
    
    private void removeAttribution(T attributer, U holder) {
        Set<U> attributionSet = attributions.get(attributer);
        attributionSet.remove(holder);
        if (attributionSet.size() == 0) {
            attributions.remove(attributer);
        }
    }
    
    private void forgetProperty(T attributer, U holder) {
        Set<T> propertySet = properties.get(holder);
        propertySet.remove(attributer);
        if (propertySet.size() == 0) {
            properties.remove(holder);
        }
    }
    
    public void remove(T attributer, U holder) {
        DenseMaps.requireAttributions(this, attributer);
        DenseMaps.requireProperties(this, holder);
        
        removeAttribution(attributer, holder);
        forgetProperty(attributer, holder);
    }
    
    public void remove(T attributer) {
        DenseMaps.requireAttributions(this, attributer);
        
        Set<U> holders = attributions.get(attributer);
        for (U holder : holders) {
            forgetProperty(attributer, holder);
        }
        
        attributions.remove(attributer);
    }
    
    public void forget(U holder) {
        DenseMaps.requireProperties(this, holder);
        
        Set<T> attributers = properties.get(holder);
        for (T attributer : attributers) {
            removeAttribution(attributer, holder);
        }
        
        properties.remove(holder);
    }
    
}
