package model.bimapper;

import java.util.Set;

import model.mapper.Mappers;
import model.mapper.MutableMultiMapper;
import model.presence.MaybeSome;

public final class BiMappers {
    private BiMappers() {}
    
    private static abstract class AbstractBiMapper<K, V> implements BiMapper<K, V>, MutableMultiMapper<K, V> {
        private final MutableMultiMapper<K, V> forwardMap;
        private final MutableMultiMapper<V, K> backwardMap;
        
        private AbstractBiMapper(MutableMultiMapper<K, V> forwardMap, MutableMultiMapper<V, K> backwardMap) {
            this.forwardMap = forwardMap;
            this.backwardMap = backwardMap;
        }
        
        private AbstractBiMapper(AbstractBiMapper<V, K> other) {
            this.forwardMap = other.backwardMap;
            this.backwardMap = other.forwardMap;
        }
        
        public MaybeSome<K> keys() {
            return forwardMap.keys();
        }
        
        public MaybeSome<V> get(K key) {
            return forwardMap.get(key);
        }
        
        public abstract boolean canMap(K key, V value);
        
        public void map(K key, V value) {
            Mappers.requireCanMap(this, key, value);
            
            forwardMap.map(key, value);
            backwardMap.map(value, key);
        }
        
        public void remap(K key, V oldValue, V newValue) {
            Mappers.requireMapping(this, key, oldValue);
            
            forwardMap.unmap(key, oldValue);
            backwardMap.unmap(oldValue, key);
            
            forwardMap.map(key, newValue);
            backwardMap.map(newValue, key);
        }
        
        public void unmap(K key, V value) {
            forwardMap.unmap(key, value);
            backwardMap.unmap(value, key);
        }
        
        public void unmapAll(K key) {
            Set<V> values = forwardMap.get(key).asSet();
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
            super(Mappers.singleMapper(), Mappers.singleMapper());
            this.directMapper = new DirectMapper<>(this);
        }
        
        private DirectMapper(DirectMapper<V, K> other) {
            super(other);
            this.directMapper = other;
        }
        
        public boolean canMap(K key, V value) {
            return !this.get(key).hasAny() && !this.inverse().get(value).hasAny();
        }
        
        public DirectMapper<V, K> inverse() {
            return this.directMapper;
        }
    }
    
    public static final class FunctionMapper<K, V> extends AbstractBiMapper<K, V> {
        // The inverse mapping.
        private final PartitionMapper<V, K> partitionMapper;
        
        private FunctionMapper() {
            super(Mappers.singleMapper(), Mappers.multiMapper());
            partitionMapper = new PartitionMapper<>(this);
        }
        
        private FunctionMapper(PartitionMapper<V, K> other) {
            super(other);
            this.partitionMapper = other;
        }
        
        public boolean canMap(K key, V value) {
            return !this.get(key).hasAny();
        }
        
        public PartitionMapper<V, K> inverse() {
            return this.partitionMapper;
        }
    }
    
    public static final class PartitionMapper<K, V> extends AbstractBiMapper<K, V> {
        // The inverse mapping.
        private final FunctionMapper<V, K> functionMapper;
        
        private PartitionMapper() {
            super(Mappers.multiMapper(), Mappers.singleMapper());
            this.functionMapper = new FunctionMapper<>(this);
        }
        
        private PartitionMapper(FunctionMapper<V, K> other) {
            super(other);
            this.functionMapper = other;
        }
        
        public boolean canMap(K key, V value) {
            return !this.inverse().get(value).hasAny();
        }
        
        public FunctionMapper<V, K> inverse() {
            return this.functionMapper;
        }
    };
    
    public static final class DenseMapper<K, V> extends AbstractBiMapper<K, V> {
        // The inverse mapping.
        private final DenseMapper<V, K> inverseMapper;
        
        private DenseMapper() {
            super(Mappers.multiMapper(), Mappers.multiMapper());
            this.inverseMapper = new DenseMapper<>(this);
        }
        
        private DenseMapper(DenseMapper<V, K> other) {
            super(other);
            this.inverseMapper = other;
        }
        
        public boolean canMap(K key, V value) {
            return !this.get(key).has(value);
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