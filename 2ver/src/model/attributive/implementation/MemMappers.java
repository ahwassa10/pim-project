package model.attributive.implementation;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import model.attributive.specification.Mapper;

public final class MemMappers {
    private interface ValueMap<K, V> {
        void map(K key, V value);
        void unmap(K key, V value);
        void unmapAll(K key);
        
        boolean hasValues(K key);
        Set<V> getValues(K key);
        Iterator<V> iterateValues(K key);
    }
    
    private static final class SingleMap<K, V> implements ValueMap<K, V> {
        private final Map<K, V> map = new HashMap<>();
        
        public void map(K key, V value) {
            map.put(key, value);
        }
        
        public void unmap(K key, V value) {
            map.remove(key);
        }
        
        public void unmapAll(K key) {
            map.remove(key);
        }
        
        public boolean hasValues(K key) {
            return map.containsKey(key);
        }
        
        public Set<V> getValues(K key) {
            return Set.of(map.get(key));
        }
        
        public Iterator<V> iterateValues(K key) {
            if (map.containsKey(key)) {
                return new Iterator<V>() {
                    private final V value = map.get(key);
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
    }
    
    private static final class ManyMap<K, V> implements ValueMap<K, V> {
        private final Map<K, Set<V>> map = new HashMap<>();
        
        public void map(K key, V value) {
            map.computeIfAbsent(key, k -> new HashSet<>()).add(value);
        }
        
        public void unmapAll(K key) {
            map.remove(key);
        }
        
        public void unmap(K key, V value) {
            Set<V> values = map.get(key);
            
            values.remove(value);
            if (values.size() == 0) {
                map.remove(key);
            }
        }
        
        public boolean hasValues(K key) {
            return map.containsKey(key);
        }
        
        public Set<V> getValues(K key) {
            return Collections.unmodifiableSet(map.get(key));
        }
        
        public Iterator<V> iterateValues(K key) {
            return Collections.unmodifiableSet(map.get(key)).iterator();
        }
    }
    
    private static abstract class AbstractMapper<K, V> implements Mapper<K, V> {
        private final ValueMap<K, V> forwardMap;
        private final ValueMap<V, K> backwardMap;
        
        public AbstractMapper(ValueMap<K, V> forwardMap, ValueMap<V, K> backwardMap) {
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
        
        public Set<V> getValues(K key) {
            Mappers.requireValues(this, key);
            
            return forwardMap.getValues(key);
        }
        
        public Iterator<V> iterateValues(K key) {
            return forwardMap.iterateValues(key);
        }
        
        public void map(K key, V value) {
            forwardMap.map(key, value);
            backwardMap.map(value, key);
        }
        
        public void unmap(K key, V value) {
            Mappers.requireMapping(this, key, value);
            
            forwardMap.unmap(key, value);
            backwardMap.unmap(value, key);
        }
        
        public void unmapAll(K key) {
            Mappers.requireValues(this, key);
            
            Set<V> values = forwardMap.getValues(key);
            for (V value : values) {
                backwardMap.unmap(value, key);
            }
        }
    }
    
    private static final class DirectMapper<K, V> extends AbstractMapper<K, V> {
        private final DirectMapper<V, K> directMapper;
        
        public DirectMapper() {
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
    
    private static final class FunctionMapper<K, V> extends AbstractMapper<K, V> {
        private final PartitionMapper<V, K> partitionMapper;
        
        public FunctionMapper() {
            super(new SingleMap<>(), new ManyMap<>());
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
    
    private static final class PartitionMapper<K, V> extends AbstractMapper<K, V> {
        private final FunctionMapper<V, K> functionMapper;
        
        public PartitionMapper() {
            super(new ManyMap<>(), new SingleMap<>());
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
    
    private static final class DenseMapper<K, V> extends AbstractMapper<K, V> {
        private final DenseMapper<V, K> inverseMapper;
        
        public DenseMapper() {
            super(new ManyMap<>(), new ManyMap<>());
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
    
    public static <K, V> Mapper<K, V> directMapper() {
        return new DirectMapper<K, V>();
    }
    
    public static <K, V> Mapper<K, V> functionMapper() {
        return new FunctionMapper<K, V>();
    }
    
    public static <K, V> Mapper<K, V> partitonMapper() {
        return new PartitionMapper<K, V>();
    }
    
    public static <K, V> Mapper<K, V> denseMapper() {
        return new DenseMapper<K, V>();
    }
}