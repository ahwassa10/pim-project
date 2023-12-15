package model.mapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import model.presence.Maybe;
import model.presence.MaybeSome;
import model.presence.None;
import model.presence.One;

public final class Mappers {
    public static final class SingleMapper<K, V> implements MutableMapper<K, V> {
        private final Map<K, V> imap = new HashMap<>();
        
        public Stream<K> keyStream() {
            return imap.keySet().stream();
        }
        
        public boolean hasMapping(K key, V value) {
            return imap.containsKey(key) && Objects.equals(imap.get(key), value);
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
        
        public Stream<K> keyStream() {
            return imap.keySet().stream();
        }
        
        public boolean hasMapping(K key, V value) {
            return imap.containsKey(key) && imap.get(key).contains(value);
        }
        
        public MaybeSome<V> get(K key) {
            if (imap.containsKey(key)) {
                return MaybeSome.of(imap.get(key));
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
        if (!mapper.get(key).has()) {
            String msg = String.format("%s is not a key in this mapper", key);
            throw new IllegalArgumentException(msg);
        }
        return key;
    }

    public static <K> K requireNoKeys(Mapper<K, ?> mapper, K key) {
        if (mapper.get(key).has()) {
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