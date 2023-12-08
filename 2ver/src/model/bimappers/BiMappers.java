package model.bimappers;

import java.util.Iterator;
import java.util.Set;

import model.mappers.MutableMapper;
import model.mappers.Mappers;
import model.mappers.Mappers.MultiMapper;
import model.mappers.Mappers.SingleMapper;

public final class BiMappers {
    private BiMappers() {}
    
    private static abstract class AbstractBiMapper<K, V> implements BiMapper<K, V>, MutableMapper<K, V> {
        private final MutableMapper<K, V> forwardMap;
        private final MutableMapper<V, K> backwardMap;
        
        private AbstractBiMapper(MutableMapper<K, V> forwardMap, MutableMapper<V, K> backwardMap) {
            this.forwardMap = forwardMap;
            this.backwardMap = backwardMap;
        }
        
        private AbstractBiMapper(AbstractBiMapper<V, K> other) {
            this.forwardMap = other.backwardMap;
            this.backwardMap = other.forwardMap;
        }
        
        public boolean hasMapping(K key, V value) {
            return forwardMap.hasMapping(key, value);
        }
        
        public int countValues(K key) {
            return forwardMap.countValues(key);
        }
        
        public Iterator<V> iterateValues(K key) {
            return forwardMap.iterateValues(key);
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
        
        public abstract boolean canMap(K key, V value);
        
        public void map(K key, V value) {
            Mappers.requireCanMap(this, key, value);
            
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
    
    public static final class DirectMapper<K, V> extends AbstractBiMapper<K, V> {
        // The inverse mapping.
        // Note that the forward and inverse mappings share the same memory backing.
        private final DirectMapper<V, K> directMapper;
        
        private DirectMapper() {
            super(new SingleMapper<K, V>(), new SingleMapper<V, K>());
            this.directMapper = new DirectMapper<>(this);
        }
        
        private DirectMapper(DirectMapper<V, K> other) {
            super(other);
            this.directMapper = other;
        }
        
        public boolean canMap(K key, V value) {
            return !this.hasValues(key) && !this.inverse().hasValues(value);
        }
        
        public DirectMapper<V, K> inverse() {
            return this.directMapper;
        }
    }
    
    public static final class FunctionMapper<K, V> extends AbstractBiMapper<K, V> {
        // The inverse mapping.
        private final PartitionMapper<V, K> partitionMapper;
        
        private FunctionMapper() {
            super(new SingleMapper<>(), new MultiMapper<>());
            partitionMapper = new PartitionMapper<>(this);
        }
        
        private FunctionMapper(PartitionMapper<V, K> other) {
            super(other);
            this.partitionMapper = other;
        }
        
        public boolean canMap(K key, V value) {
            return !this.hasValues(key);
        }
        
        public PartitionMapper<V, K> inverse() {
            return this.partitionMapper;
        }
    }
    
    public static final class PartitionMapper<K, V> extends AbstractBiMapper<K, V> {
        // The inverse mapping.
        private final FunctionMapper<V, K> functionMapper;
        
        private PartitionMapper() {
            super(new MultiMapper<>(), new SingleMapper<>());
            this.functionMapper = new FunctionMapper<>(this);
        }
        
        private PartitionMapper(FunctionMapper<V, K> other) {
            super(other);
            this.functionMapper = other;
        }
        
        public boolean canMap(K key, V value) {
            return !this.inverse().hasValues(value);
        }
        
        public FunctionMapper<V, K> inverse() {
            return this.functionMapper;
        }
    };
    
    public static final class DenseMapper<K, V> extends AbstractBiMapper<K, V> {
        // The inverse mapping.
        private final DenseMapper<V, K> inverseMapper;
        
        private DenseMapper() {
            super(new MultiMapper<>(), new MultiMapper<>());
            this.inverseMapper = new DenseMapper<>(this);
        }
        
        private DenseMapper(DenseMapper<V, K> other) {
            super(other);
            this.inverseMapper = other;
        }
        
        public boolean canMap(K key, V value) {
            return !this.hasMapping(key, value);
        }
        
        public BiMapper<V, K> inverse() {
            return this.inverseMapper;
        }
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