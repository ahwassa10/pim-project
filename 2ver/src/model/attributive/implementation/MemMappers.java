package model.attributive.implementation;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import model.attributive.specification.BasedMap;
import model.attributive.specification.Mapper;

public final class MemMappers {
    public static final class SingleMap<K, V> implements BasedMap<K, V> {
        private final Map<K, V> imap = new HashMap<>();
        
        public boolean hasValues(K key) {
            return imap.containsKey(key);
        }
        
        public V anyValue(K key) {
            Mappers.requireValues(this, key);
            return imap.get(key);
        }
        
        public Set<V> getValues(K key) {
            Mappers.requireValues(this, key);
            return Set.of(imap.get(key));
        }
        
        public Iterator<V> iterateValues(K key) {
            if (imap.containsKey(key)) {
                return new Iterator<V>() {
                    private final V value = imap.get(key);
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
        
        public boolean hasMapping(K key, V value) {
            return imap.containsKey(key) && Objects.equals(imap.get(key), value);
        }
        
        public void map(K key, V value) {
            Mappers.requireNoValues(this, key);
            imap.put(key, value);
        }
        
        public void unmap(K key, V value) {
            Mappers.requireValues(this, key);
            imap.remove(key);
        }
        
        public void unmapAll(K key) {
            Mappers.requireValues(this, key);
            imap.remove(key);
        }
    }
    
    public static final class MultiMap<K, V> implements BasedMap<K, V> {
        private final Map<K, Set<V>> imap = new HashMap<>();
        
        public boolean hasValues(K key) {
            return imap.containsKey(key);
        }
        
        public V anyValue(K key) {
            Mappers.requireValues(this, key);
            return imap.get(key).iterator().next();
        }
        
        public Set<V> getValues(K key) {
            Mappers.requireValues(this, key);
            return Collections.unmodifiableSet(imap.get(key));
        }
        
        public Iterator<V> iterateValues(K key) {
            return Collections.unmodifiableSet(imap.get(key)).iterator();
        }
        
        public boolean hasMapping(K key, V value) {
            return imap.containsKey(key) && imap.get(key).contains(value);
        }
        
        public void map(K key, V value) {
            Mappers.requireNoMapping(this, key, value);
            imap.computeIfAbsent(key, k -> new HashSet<>()).add(value);
        }
        
        public void unmapAll(K key) {
            Mappers.requireValues(this, key);
            imap.remove(key);
        }
        
        public void unmap(K key, V value) {
            Mappers.requireMapping(this, key, value);
            
            Set<V> values = imap.get(key);
            values.remove(value);
            if (values.size() == 0) {
                imap.remove(key);
            }
        }
    }
    
    private static abstract class AbstractMapper<K, V> implements Mapper<K, V> {
        private final BasedMap<K, V> forwardMap;
        private final BasedMap<V, K> backwardMap;
        
        public AbstractMapper(BasedMap<K, V> forwardMap, BasedMap<V, K> backwardMap) {
            this.forwardMap = forwardMap;
            this.backwardMap = backwardMap;
        }
        
        public AbstractMapper(AbstractMapper<V, K> other) {
            this.forwardMap = other.backwardMap;
            this.backwardMap = other.forwardMap;
        }
        
        public boolean hasValues(K key) {
            return forwardMap.hasValues(key);
        }
        
        public V anyValue(K key) {
            return forwardMap.anyValue(key);
        }
        
        public Set<V> getValues(K key) {
            return forwardMap.getValues(key);
        }
        
        public Iterator<V> iterateValues(K key) {
            return forwardMap.iterateValues(key);
        }
        
        public boolean hasMapping(K key, V value) {
            return forwardMap.hasMapping(key, value);
        }
        
        public void map(K key, V value) {
            forwardMap.map(key, value);
            backwardMap.map(value, key);
        }
        
        public void unmap(K key, V value) {
            forwardMap.unmap(key, value);
            backwardMap.unmap(value, key);
        }
        
        public void unmapAll(K key) {
            Set<V> values = forwardMap.getValues(key);
            for (V value : values) {
                backwardMap.unmap(value, key);
            }
            forwardMap.unmapAll(key);
        }
    }
    
    public static final class DirectMapper<K, V> extends AbstractMapper<K, V> {
        private final DirectMapper<V, K> directMapper;
        
        private DirectMapper() {
            super(new SingleMap<K, V>(), new SingleMap<V, K>());
            this.directMapper = new DirectMapper<>(this);
        }
        
        private DirectMapper(DirectMapper<V, K> other) {
            super(other);
            this.directMapper = other;
        }
        
        public void map(K key, V value) {
            Mappers.requireNoValues(this, key);
            Mappers.requireNoKeys(this, value);
            
            super.map(key, value);
        }
        
        public DirectMapper<V, K> inverse() {
            return this.directMapper;
        }
    }
    
    public static final class FunctionMapper<K, V> extends AbstractMapper<K, V> {
        private final PartitionMapper<V, K> partitionMapper;
        
        private FunctionMapper() {
            super(new SingleMap<>(), new MultiMap<>());
            partitionMapper = new PartitionMapper<>(this);
        }
        
        private FunctionMapper(PartitionMapper<V, K> other) {
            super(other);
            this.partitionMapper = other;
        }
        
        public void map(K key, V value) {
            Mappers.requireNoValues(this, key);
            
            super.map(key, value);
        }
        
        public PartitionMapper<V, K> inverse() {
            return this.partitionMapper;
        }
    }
    
    public static final class PartitionMapper<K, V> extends AbstractMapper<K, V> {
        private final FunctionMapper<V, K> functionMapper;
        
        private PartitionMapper() {
            super(new MultiMap<>(), new SingleMap<>());
            this.functionMapper = new FunctionMapper<>(this);
        }
        
        private PartitionMapper(FunctionMapper<V, K> other) {
            super(other);
            this.functionMapper = other;
        }
        
        public void map(K key, V value) {
            Mappers.requireNoKeys(this, value);
            
            super.map(key, value);
        }
        
        public FunctionMapper<V, K> inverse() {
            return this.functionMapper;
        }
    };
    
    public static final class DenseMapper<K, V> extends AbstractMapper<K, V> {
        private final DenseMapper<V, K> inverseMapper;
        
        private DenseMapper() {
            super(new MultiMap<>(), new MultiMap<>());
            this.inverseMapper = new DenseMapper<>(this);
        }
        
        private DenseMapper(DenseMapper<V, K> other) {
            super(other);
            this.inverseMapper = other;
        }
        
        public void map(K key, V value) {
            Mappers.requireNoMapping(this, key, value);
            
            super.map(key, value);
        }
        
        public Mapper<V, K> inverse() {
            return this.inverseMapper;
        }
    }
    
    public static <K, V> SingleMap<K, V> singleMap() {
        return new SingleMap<K, V>();
    }
    
    public static <K, V> MultiMap<K, V> multiMap() {
        return new MultiMap<K, V>();
    }
    
    public static <K, V> DirectMapper<K, V> directMapper() {
        return new DirectMapper<K, V>();
    }
    
    public static <K, V> FunctionMapper<K, V> functionMapper() {
        return new FunctionMapper<K, V>();
    }
    
    public static <K, V> PartitionMapper<K, V> partitonMapper() {
        return new PartitionMapper<K, V>();
    }
    
    public static <K, V> DenseMapper<K, V> denseMapper() {
        return new DenseMapper<K, V>();
    }
}