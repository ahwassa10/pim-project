package model.attributive.implementation;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import model.attributive.specification.Mapper;

public final class PartitionMapper<K, V> implements Mapper<K, V> {
    final Map<K, Set<V>> forwardMap;
    
    final Map<V, K> backwardMap;
    
    private final FunctionMapper<V, K> functionMapper;
    
    public PartitionMapper() {
        this.forwardMap = new HashMap<>();
        this.backwardMap = new HashMap<>();
        this.functionMapper = new FunctionMapper<>(this);
    }
    
    PartitionMapper(FunctionMapper<V, K> other) {
        this.forwardMap = other.backwardMap;
        this.backwardMap = other.forwardMap;
        this.functionMapper = other;
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
            return Collections.unmodifiableSet(forwardMap.get(key)).iterator();
        } else {
            return Collections.emptyIterator();
        }
    }
    
    public void map(K key, V value) {
        Mappers.requireNoKeys(this, value);
        
        forwardMap.computeIfAbsent(key, k -> new HashSet<>()).add(value);
        backwardMap.put(value, key);
    }
    
    private void removeForward(K key, V value) {
        Set<V> values = forwardMap.get(key);
        
        values.remove(value);
        if (values.size() == 0) {
            forwardMap.remove(key);
        }
    }
    
    public void unmap(K key, V value) {
        Mappers.requireMapping(this, key, value);
        
        removeForward(key, value);
        backwardMap.remove(value, key);
    }
    
    public void unmapAll(K key) {
        Mappers.requireValues(this, key);
        
        Set<V> values = forwardMap.remove(key);
        
        // To delete a key, we need to delete all the value->key back references.
        for(V value : values) {
            backwardMap.remove(value, key);
        }
    }
    
    public Mapper<V, K> inverse() {
        return this.functionMapper;
    }
}