package model.mappers;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import model.util.SingleIterators;

public final class Mappers {
    public static final class SingleMapper<K, V> implements MutableMapper<K, V> {
        private final Map<K, V> imap = new HashMap<>();
        
        public boolean hasMapping(K key, V value) {
            return imap.containsKey(key) && Objects.equals(imap.get(key), value);
        }
        
        public int countValues(K key) {
            return imap.containsKey(key) ? 1 : 0;
        }
        
        public Iterator<V> iterateValues(K key) {
            if (imap.containsKey(key)) {
                return SingleIterators.of(imap.get(key));
            } else {
                return Collections.emptyIterator();
            }
        }
        
        public boolean hasKey(K key) {
            return imap.containsKey(key);
        }
        
        public V anyValue(K key) {
            Mappers.requireKey(this, key);
            return imap.get(key);
        }
        
        public Set<V> getValues(K key) {
            Mappers.requireKey(this, key);
            return Set.of(imap.get(key));
        }
        
        public boolean canMap(K key, V value) {
            return !hasKey(key);
        }
        
        public void map(K key, V value) {
            Mappers.requireCanMap(this, key, value);
            imap.put(key, value);
        }
        
        public void unmap(K key, V value) {
            Mappers.requireMapping(this, key, value);
            imap.remove(key);
        }
        
        public void unmapAll(K key) {
            Mappers.requireKey(this, key);
            imap.remove(key);
        }
    }
    
    public static final class MultiMapper<K, V> implements MutableMapper<K, V> {
        private final Map<K, Set<V>> imap = new HashMap<>();
        
        public boolean hasMapping(K key, V value) {
            return imap.containsKey(key) && imap.get(key).contains(value);
        }
        
        public int countValues(K key) {
            if (imap.containsKey(key)) {
                return imap.get(key).size();
            } else {
                return 0;
            }
        }
        
        public Iterator<V> iterateValues(K key) {
            if (imap.containsKey(key)) {
                return Set.copyOf(imap.get(key)).iterator();
            } else {
                return Collections.emptyIterator();
            }
        }
        
        public boolean hasKey(K key) {
            return imap.containsKey(key);
        }
        
        public V anyValue(K key) {
            Mappers.requireKey(this, key);
            return imap.get(key).iterator().next();
        }
        
        public Set<V> getValues(K key) {
            Mappers.requireKey(this, key);
            return Set.copyOf(imap.get(key));
        }
        
        public boolean canMap(K key, V value) {
            return !imap.containsKey(key) || !imap.get(key).contains(value);
        }
        
        public void map(K key, V value) {
            Mappers.requireCanMap(this, key, value);
            imap.computeIfAbsent(key, k -> new HashSet<>()).add(value);
        }
        
        public void unmap(K key, V value) {
            Mappers.requireMapping(this, key, value);
            
            Set<V> values = imap.get(key);
            values.remove(value);
            if (values.size() == 0) {
                imap.remove(key);
            }
        }
        
        public void unmapAll(K key) {
            Mappers.requireKey(this, key);
            imap.remove(key);
        }
    }
    
    public static <K, V> SingleMapper<K, V> singleMapper() {
        return new SingleMapper<K, V>();
    }
    
    public static <K, V> MultiMapper<K, V> multiMapper() {
        return new MultiMapper<K, V>();
    }
    
    public static <K, V> void requireMapping(Mapper<K, V> mapper, K key, V value) {
        if (!mapper.hasMapping(key, value)) {
            String msg = String.format("%s is not mapped to %s under this mapper", key, value);
            throw new IllegalArgumentException(msg);
        }
    }

    public static <K, V> void requireNoMapping(Mapper<K, V> mapper, K key, V value) {
        if (mapper.hasMapping(key, value)) {
            String msg = String.format("%s is already mapped to %s under this mapper", key, value);
            throw new IllegalArgumentException(msg);
        }
    }

    public static <K> K requireKey(Mapper<K, ?> mapper, K key) {
        if (!mapper.hasKey(key)) {
            String msg = String.format("%s is not a key in this mapper", key);
            throw new IllegalArgumentException(msg);
        }
        return key;
    }

    public static <K> K requireNoKeys(Mapper<K, ?> mapper, K key) {
        if (mapper.hasKey(key)) {
            String msg = String.format("%s is a key in this mapper", key);
            throw new IllegalArgumentException(msg);
        }
        return key;
    }

    public static <K, V> void requireCanMap(MutableMapper<K,V> mapper, K key, V value) {
        if (!mapper.canMap(key, value)) {
            String msg = String.format("%s cannot be mapped to %s under this mapper", key, value);
            throw new IllegalArgumentException(msg);
        }
    }
}