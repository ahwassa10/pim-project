package model.attributive.implementation;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import model.attributive.specification.Mapper;

public final class MemoryMappers {
    private static final class SingleMap<K, V> {
        private final Map<K, V> map = new HashMap<>();
        
        public void add(K key, V value) {
            map.put(key, value);
        }
        
        public void remove(K key) {
            map.remove(key);
        }
        
        public boolean hasValues(K key) {
            return map.containsKey(key);
        }
        
        public V getValue(K key) {
            return map.get(key);
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
    
    private static final class ManyMap<K, V> {
        private final Map<K, Set<V>> map = new HashMap<>();
        
        public void add(K key, V value) {
            map.computeIfAbsent(key, k -> new HashSet<>()).add(value);
        }
        
        public void removeAll(K key) {
            map.remove(key);
        }
        
        public void remove(K key, V value) {
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
    
    private static final class DirectMapper<K, V> implements Mapper<K, V> {
        private final SingleMap<K, V> forwardMap;
        private final SingleMap<V, K> backwardMap;
        private final DirectMapper<V, K> directMapper;
        
        public DirectMapper() {
            this.forwardMap = new SingleMap<K, V>();
            this.backwardMap = new SingleMap<V, K>();
            this.directMapper = new DirectMapper<>(this);
        }
        
        private DirectMapper(DirectMapper<V, K> other) {
            this.forwardMap = other.backwardMap;
            this.backwardMap = other.forwardMap;
            this.directMapper = other;
        }
        
        public boolean hasValues(K key) {
            return forwardMap.hasValues(key);
        }
        
        public Set<V> getValues(K key) {
            return forwardMap.getValues(key);
        }
        
        public Iterator<V> iterateValues(K key) {
            return forwardMap.iterateValues(key);
        }
        
        public void map(K key, V value) {
            forwardMap.add(key, value);
            backwardMap.add(value, key);
        }
        
        public void unmap(K key, V value) {
            forwardMap.remove(key);
            backwardMap.remove(value);
        }
        
        public void unmapAll(K key) {
            V value = forwardMap.getValue(key);
            forwardMap.remove(key);
            backwardMap.remove(value);
        }
        
        public DirectMapper<V, K> inverse() {
            return this.directMapper;
        }
    }
    
    private static final class FunctionMapper<K, V> implements Mapper<K, V> {
        private final SingleMap<K, V> forwardMap;
        private final ManyMap<V, K> backwardMap;
        
        private final PartitionMapper<V, K> partitionMapper;
        
        public FunctionMapper() {
            forwardMap = new SingleMap<>();
            backwardMap = new ManyMap<>();
            partitionMapper = new PartitionMapper<>(this);
        }
        
        FunctionMapper(PartitionMapper<V, K> other) {
            this.forwardMap = other.backwardMap;
            this.backwardMap = other.forwardMap;
            this.partitionMapper = other;
        }
        
        public boolean hasValues(K key) {
            return forwardMap.hasValues(key);
        }
        
        public Set<V> getValues(K key) {
            return forwardMap.getValues(key);
        }
        
        public Iterator<V> iterateValues(K key) {
            return forwardMap.iterateValues(key);
        }
        
        public void map(K key, V value) {
            forwardMap.add(key, value);
            backwardMap.add(value, key);
        }
        
        public void unmap(K key, V value) {
            forwardMap.remove(key);
            backwardMap.remove(value, key);
        }
        
        public void unmapAll(K key) {
            V value = forwardMap.getValue(key);
            forwardMap.remove(key);
            backwardMap.remove(value, key);
        }
        
        public PartitionMapper<V, K> inverse() {
            return this.partitionMapper;
        }
    }
    
    private static final class PartitionMapper<K, V> implements Mapper<K, V> {
        private final ManyMap<K, V> forwardMap;
        private final SingleMap<V, K> backwardMap;
        
        private final FunctionMapper<V, K> functionMapper;
        
        public PartitionMapper() {
            this.forwardMap = new ManyMap<>();
            this.backwardMap = new SingleMap<>();
            this.functionMapper = new FunctionMapper<>(this);
        }
        
        PartitionMapper(FunctionMapper<V, K> other) {
            this.forwardMap = other.backwardMap;
            this.backwardMap = other.forwardMap;
            this.functionMapper = other;
        }
        
        public boolean hasValues(K key) {
            return forwardMap.hasValues(key);
        }
        
        public Set<V> getValues(K key) {
            return forwardMap.getValues(key);
        }
        
        public Iterator<V> iterateValues(K key) {
            return forwardMap.iterateValues(key);
        }
        
        public void map(K key, V value) {
            forwardMap.add(key, value);
            backwardMap.add(value, key);
        }
        
        public void unmap(K key, V value) {
            forwardMap.remove(key, value);
            backwardMap.remove(value);
        }
        
        public void unmapAll(K key) {
            Set<V> values = forwardMap.getValues(key);
            forwardMap.removeAll(key);
            
            for (V value : values) {
                backwardMap.remove(value);
            }
        }
        
        public FunctionMapper<V, K> inverse() {
            return this.functionMapper;
        }
    };
    
    private static final class DenseMapper<K, V> implements Mapper<K, V> {
        private final ManyMap<K, V> forwardMap;
        private final ManyMap<V, K> backwardMap;
        private final DenseMapper<V, K> inverseMapper;
        
        public DenseMapper() {
            this.forwardMap = new ManyMap<>();
            this.backwardMap = new ManyMap<>();
            this.inverseMapper = new DenseMapper<>(this);
        }
        
        // Used to create the inverse mapping
        private DenseMapper(DenseMapper<V, K> other) {
            this.forwardMap = other.backwardMap;
            this.backwardMap = other.forwardMap;
            this.inverseMapper = other;
        }
        
        public boolean hasValues(K key) {
            return forwardMap.hasValues(key);
        }
        
        public Set<V> getValues(K key) {
            return forwardMap.getValues(key);
        }
        
        public Iterator<V> iterateValues(K key) {
            return forwardMap.iterateValues(key);
        }
        
        public void map(K key, V value) {
            forwardMap.add(key, value);
            backwardMap.add(value, key);
        }
        
        public void unmap(K key, V value) {
            forwardMap.remove(key, value);
            backwardMap.remove(value, key);
        }
        
        public void unmapAll(K key) {
            Set<V> values = forwardMap.getValues(key);
            forwardMap.removeAll(key);
            
            for (V value : values) {
                backwardMap.remove(value, key);
            }
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