package model.attributive.implementation;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import model.attributive.specification.Mapper;
import model.attributive.specification.View;

public class MemoryMapper<K, V> implements Mapper<K, V> {
    private final Map<K, Set<V>> forwardMap = new HashMap<>();
    
    private final Map<V, Set<K>> backwardMap = new HashMap<>();
    
    private final View<K, V> forwardView = new BasicView<>(forwardMap);
    
    private final View<V, K> backwardView = new BasicView<>(backwardMap);
    
    private static final class BasicView<K, V> implements View<K, V> {
        private final Map<K, Set<V>> map;
        
        public BasicView(Map<K, Set<V>> map) {
            this.map = map;
        }
        
        public boolean hasMappings(K key) {
            return map.containsKey(key);
        }
        
        public Set<V> getMappings(K key) {
            if (!map.containsKey(key)) {
                String msg = String.format("%s is not mapped to any value", key);
                throw new IllegalArgumentException(msg);
            }
            return Collections.unmodifiableSet(map.get(key));
        }
        
        public Iterator<V> iterateMappings(K key) {
            if (map.containsKey(key)) {
                return Collections.unmodifiableSet(map.get(key)).iterator();
            } else {
                return Collections.emptyIterator();
            }
        }
    }
    
    public View<K, V> forward() {
        return forwardView;
    }
    
    public View<V, K> backward() {
        return backwardView;
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
    
    public void delete(K key) {
        Mappers.requireValues(this, key);
        
        Set<V> values = forwardMap.remove(key);
        
        // To delete a key, we need to delete all the value->key back references.
        for(V value : values) {
            removeBackward(value, key);
        }
    }
    
    public void forget(V value) {
        Mappers.requirePropertizations(this, value);
        
        Set<K> keys = backwardMap.remove(value);
        
        // To forget a value, we need to remove all the key-value forward references.
        for (K key : keys) {
            removeForward(key, value);
        }
    }
}