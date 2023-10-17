package model.attributive.implementation;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import model.attributive.specification.FunctionMap;

public final class HashFunctionMap<T, U> implements FunctionMap<T, U> {
    private final Map<T, U> properties = new HashMap<>();
    
    private final Map<U, Set<T>> attributes = new HashMap<>();
    
    public boolean hasProperties(T attributer) {
        return properties.containsKey(attributer);
    }
    
    public U getProperty(T attributer) {
        return properties.get(attributer);
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
        properties.put(attributer, holder);
        attributes.computeIfAbsent(holder, h -> new HashSet<>()).add(attributer);
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
        
        properties.remove(attributer, holder);
        forgetPropertization(attributer, holder);
    }
    
    public void remove(T attributer) {
        DenseMaps.requireProperties(this, attributer);
        
        U holder = properties.get(attributer);
        forgetPropertization(attributer, holder);
        properties.remove(attributer, holder);
    }
    
    public void forget(U holder) {
        DenseMaps.requireAttributes(this, holder);
        
        Set<T> attributers = attributes.get(holder);
        for (T attributer : attributers) {
            properties.remove(attributer, holder);
        }
        
        attributes.remove(holder);
    }
}
