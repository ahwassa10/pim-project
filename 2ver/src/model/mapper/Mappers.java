package model.mapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.presence.Maybe;
import model.presence.MaybeSome;
import model.presence.None;
import model.presence.One;
import model.presence.Some;

public final class Mappers {
    private static final class ImpSingleMapper<K, V> implements MutableSingleMapper<K, V> {
        private final Map<K, V> imap = new HashMap<>();
        
        public MaybeSome<K> keys() {
            if (imap.size() == 0) {
                return None.of();
            } else {
                return Some.of(imap.keySet());
            }
        }
        
        public Maybe<V> get(K key) {
            if (imap.containsKey(key)) {
                return One.of(imap.get(key));
            } else {
                return None.of();
            }
        }
        
        public boolean canMap(K key, V value) {
            return !imap.containsKey(key);
        }
        
        public void map(K key, V value) {
            Mappers.requireCanMap(this, key, value);
            imap.put(key, value);
        }
        
        public void remap(K key, V oldValue, V newValue) {
            Mappers.requireMapping(this, key, oldValue);
            imap.remove(key);
            imap.put(key, newValue);
        }
        
        public void remap(K key, V value) {
            Mappers.requireKey(this, key);
            imap.put(key, value);
        }
        
        public void unmap(K key, V value) {
            Mappers.requireMapping(this, key, value);
            imap.remove(key);
        }
        
        public void unmap(K key) {
            Mappers.requireKey(this, key);
            imap.remove(key);
        }
        
        public void unmapAll(K key) {
            Mappers.requireKey(this, key);
            imap.remove(key);
        }
    }
    
    private static final class ImpMultiMapper<K, V> implements MutableMultiMapper<K, V> {
        private final Map<K, Set<V>> imap = new HashMap<>();
        
        public MaybeSome<K> keys() {
            if (imap.size() == 0) {
                return None.of();
            } else {
                return Some.of(imap.keySet());
            }
        }
        
        public MaybeSome<V> get(K key) {
            if (imap.containsKey(key)) {
                return Some.of(imap.get(key));
            } else {
                return None.of();
            }
        }
        
        public boolean canMap(K key, V value) {
            return !imap.containsKey(key) || !imap.get(key).contains(value);
        }
        
        public void map(K key, V value) {
            Mappers.requireCanMap(this, key, value);
            imap.computeIfAbsent(key, k -> new HashSet<>()).add(value);
        }
        
        public void remap(K key, V oldValue, V newValue) {
            Mappers.requireMapping(this, key, oldValue);
            
            Set<V> values = imap.get(key);
            values.remove(oldValue);
            values.add(newValue);
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
    
    public static <K, V> MutableSingleMapper<K, V> singleMapper() {
        return new ImpSingleMapper<K, V>();
    }
    
    public static <K, V> MutableMultiMapper<K, V> multiMapper() {
        return new ImpMultiMapper<K, V>();
    }
    
    public static <K, V> void requireMapping(MultiMapper<K, V> mapper, K key, V value) {
        if (!mapper.get(key).has(value)) {
            String msg = String.format("%s is not mapped to %s under this mapper", key, value);
            throw new IllegalArgumentException(msg);
        }
    }

    public static <K, V> void requireNoMapping(MultiMapper<K, V> mapper, K key, V value) {
        if (mapper.get(key).has(value)) {
            String msg = String.format("%s is already mapped to %s under this mapper", key, value);
            throw new IllegalArgumentException(msg);
        }
    }

    public static <K> K requireKey(MultiMapper<K, ?> mapper, K key) {
        if (!mapper.get(key).hasAny()) {
            String msg = String.format("%s is not a key in this mapper", key);
            throw new IllegalArgumentException(msg);
        }
        return key;
    }

    public static <K> K requireNoKeys(MultiMapper<K, ?> mapper, K key) {
        if (mapper.get(key).hasAny()) {
            String msg = String.format("%s is a key in this mapper", key);
            throw new IllegalArgumentException(msg);
        }
        return key;
    }

    public static <K, V> void requireCanMap(MutableMultiMapper<K,V> mapper, K key, V value) {
        if (!mapper.canMap(key, value)) {
            String msg = String.format("%s cannot be mapped to %s under this mapper", key, value);
            throw new IllegalArgumentException(msg);
        }
    }
}