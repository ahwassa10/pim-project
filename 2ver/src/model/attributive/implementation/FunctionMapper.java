package model.attributive.implementation;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import model.attributive.specification.Mapper;

public final class FunctionMapper<K, V> implements Mapper<K, V> {
    final Map<K, V> forwardMap;
    
    final Map<V, Set<K>> backwardMap;
    
    private final PartitionMapper<V, K> partitionMapper;
    
    public FunctionMapper() {
        forwardMap = new HashMap<>();
        backwardMap = new HashMap<>();
        partitionMapper = new PartitionMapper<>(this);
    }
    
    FunctionMapper(PartitionMapper<V, K> other) {
        this.forwardMap = other.backwardMap;
        this.backwardMap = other.forwardMap;
        this.partitionMapper = other;
    }
    
    public boolean hasValues(K key) {
        return forwardMap.containsKey(key);
    }
    
    public Set<V> getValues(K key) {
        Mappers.requireValues(this, key);
        
        return Set.of(forwardMap.get(key));
    }
    
    public V getValue(K key) {
        Mappers.requireValues(this, key);
        
        return forwardMap.get(key);
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
        
        forwardMap.put(key, value);
        backwardMap.computeIfAbsent(value, v -> new HashSet<>()).add(key);
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
        
        forwardMap.remove(key, value);
        removeBackward(value, key);
    }
    
    public void unmapAll(K key) {
        Mappers.requireValues(this, key);
        
        V value = forwardMap.remove(key);
        removeBackward(value, key);
    }
    
    public PartitionMapper<V, K> inverse() {
        return this.partitionMapper;
    }
}
