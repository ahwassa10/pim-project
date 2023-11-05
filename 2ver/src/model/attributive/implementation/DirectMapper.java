package model.attributive.implementation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import model.attributive.specification.Mapper;

public final class DirectMapper<K, V> implements Mapper<K, V> {
    private final Map<K, V> forwardMap;
    
    private final Map<V, K> backwardMap;
    
    private final DirectMapper<V, K> directMapper;
    
    public DirectMapper() {
        this.forwardMap = new HashMap<>();
        this.backwardMap = new HashMap<>();
        this.directMapper = new DirectMapper<>(this);
    }
    
    private DirectMapper(DirectMapper<V, K> other) {
        this.forwardMap = other.backwardMap;
        this.backwardMap = other.forwardMap;
        this.directMapper = other;
    }
    
    public boolean hasValues(K key) {
        return forwardMap.containsKey(key);
    }
    
    public Set<V> getValues(K key) {
        Mappers.requireValues(this, key);
        
        return Set.of(forwardMap.get(key));
    }
    
    public Iterator<V> iterateValues(K key) {
        if (forwardMap.containsKey(key)) {
            return new Iterator<V>() {
                private final V value = forwardMap.get(key);
                private boolean hasNext = true;
                
                public boolean hasNext() {
                    return hasNext;
                }
                
                public V next() {
                    if (!hasNext) {
                        throw new NoSuchElementException();
                    }
                    hasNext = false;
                    return value;
                }
            };
        } else {
            return Collections.emptyIterator();
        }
    }
    
    public void map(K key, V value) {
        Mappers.requireNoValues(this, key);
        Mappers.requireNoKeys(this, value);
        
        forwardMap.put(key, value);
        backwardMap.put(value, key);
    }
    
    public void unmap(K key, V value) {
        Mappers.requireMapping(this, key, value);
        
        forwardMap.remove(key);
        backwardMap.remove(value);
    }
    
    public void unmapAll(K key) {
        Mappers.requireValues(this, key);
        
        V value = forwardMap.remove(key);
        backwardMap.remove(value);
    }
    
    public DirectMapper<V, K> inverse() {
        return this.directMapper;
    }
}