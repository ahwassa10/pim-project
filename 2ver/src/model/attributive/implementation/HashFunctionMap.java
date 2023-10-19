package model.attributive.implementation;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import model.attributive.specification.FunctionMap;

public final class HashFunctionMap<T, U> implements FunctionMap<T, U> {
    private final Map<T, U> attributions = new HashMap<>();
    
    private final Map<U, Set<T>> attributes = new HashMap<>();
    
    public boolean hasAttributions(T attributer) {
        return attributions.containsKey(attributer);
    }
    
    public U getAttribution(T attributer) {
        return attributions.get(attributer);
    }
    
    public Iterator<U> iterateAttributions(T attributer) {
        if (attributions.containsKey(attributer)) {
            return new Iterator<U>() {
                private boolean hasNext = true;
                
                public boolean hasNext() {
                    return hasNext;
                }
                
                public U next() {
                    if (hasNext) {
                        hasNext = false;
                        return attributions.get(attributer);
                    } else {
                        throw new NoSuchElementException();
                    }
                }
            };
        } else {
            return Collections.emptyIterator();
        }
    }
    
    public boolean hasAttributes(U object) {
        return attributes.containsKey(object);
    }
    
    public Set<T> getAttributes(U object) {
        Maps.requireAttributes(this, object);
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
        Maps.requireNoAttributions(this, attributer);
        
        attributions.put(attributer, object);
        attributes.computeIfAbsent(object, h -> new HashSet<>()).add(attributer);
    }
    
    private void forgetAttribute(T attributer, U object) {
        Set<T> attributers = attributes.get(object);
        attributers.remove(attributer);
        if (attributers.size() == 0) {
            attributes.remove(object);
        }
    }
    
    public void remove(T attributer, U object) {
        Maps.requireAttributions(this, attributer);
        Maps.requireAttributes(this, object);
        
        attributions.remove(attributer, object);
        forgetAttribute(attributer, object);
    }
    
    public void remove(T attributer) {
        Maps.requireAttributions(this, attributer);
        
        U object = attributions.get(attributer);
        forgetAttribute(attributer, object);
        attributions.remove(attributer, object);
    }
    
    public void forget(U object) {
        Maps.requireAttributes(this, object);
        
        Set<T> attributers = attributes.get(object);
        for (T attributer : attributers) {
            attributions.remove(attributer, object);
        }
        
        attributes.remove(object);
    }
}
