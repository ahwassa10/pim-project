package model.attributive.implementation;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import model.attributive.specification.Mapper;

public class MemoryMapper<K, V> implements Mapper<K, V> {
    private final Map<K, Set<V>> forwardMap;
    
    private final Map<V, Set<K>> backwardMap;
    
    private final MemoryMapper<V, K> inverseMapper;
    
    public MemoryMapper() {
        this.forwardMap = new HashMap<>();
        this.backwardMap = new HashMap<>();
        this.inverseMapper = new MemoryMapper<>(this);
    }
    
    // Used to create the inverse mapping
    private MemoryMapper(MemoryMapper<V, K> other) {
        this.forwardMap = other.backwardMap;
        this.backwardMap = other.forwardMap;
        this.inverseMapper = other;
    }
    
    public boolean hasValues(K key) {
        return forwardMap.containsKey(key);
    }
    
    public Set<V> getValues(K key) {
        Mappers.requireValues(this, key);
        
        return Collections.unmodifiableSet(forwardMap.get(key));
    }
    
    public Iterator<V> iterateValues(K key) {
        if (forwardMap.containsKey(key)) {
            // Ensures that the iterator is also immutable.
            return Collections.unmodifiableSet(forwardMap.get(key)).iterator();
        } else {
            return Collections.emptyIterator();
        }
    }
    
    public void map(K key, V value) {
        Mappers.requireNoMapping(this, key, value);
        
        forwardMap.computeIfAbsent(key, k -> new HashSet<>()).add(value);
        backwardMap.computeIfAbsent(value, v -> new HashSet<>()).add(key);
    }
    
    private void removeForward(K key, V value) {
        Set<V> values = forwardMap.get(key);
        
        values.remove(value);
        if (values.size() == 0) {
            forwardMap.remove(key);
        }
    }
    
    private void removeBackward(V value, K key) {
        Set<K> keys = backwardMap.get(value);
        
        keys.remove(key);
        if(keys.size() == 0) {
            backwardMap.remove(value);
        }
    }
    
    public void unmap(K key, V value) {
        Mappers.requireMapping(this, key, value);
        
        removeForward(key, value);
        removeBackward(value, key);
    }
    
    public void unmapAll(K key) {
        Mappers.requireValues(this, key);
        
        Set<V> values = forwardMap.remove(key);
        
        // To delete a key, we need to delete all the value->key back references.
        for(V value : values) {
            removeBackward(value, key);
        }
    }
    
    public Mapper<V, K> inverse() {
        return this.inverseMapper;
    }
}