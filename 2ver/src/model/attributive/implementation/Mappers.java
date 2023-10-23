package model.attributive.implementation;

import model.attributive.specification.Mapper;
import model.attributive.specification.View;

public final class Mappers {
    private Mappers() {}
    
    public static <K> K requireValues(Mapper<K, ?> mapper, K key) {
        if (!mapper.forward().hasMappings(key)) {
            String msg = String.format("%s has not been mapped to any values under this mapper", key);
            throw new IllegalArgumentException(msg);
        }
        return key;
    }
    
    public static <V> V requirePropertizations(Mapper<?, V> mapper, V value) {
        if (!mapper.backward().hasMappings(value)) {
            String msg = String.format("No key maps to %s under this mapper", value);
            throw new IllegalArgumentException(msg);
        }
        return value;
    }
    
    public static <K, V> void requireMapping(Mapper<K, V> mapper, K key, V value) {
        View<K, V> forward = mapper.forward();
        
        if (forward.hasMappings(key) && 
                !forward.getMappings(key).contains(value)) {
            String msg = String.format("%s is not mapped to %s under this mapper", key, value);
            throw new IllegalArgumentException(msg);
        }
    }
    
    public static <K, V> void requireNoMapping(Mapper<K, V> mapper, K key, V value) {
        View<K, V> forward = mapper.forward();
        
        if (forward.hasMappings(key) &&
                forward.getMappings(key).contains(value)) {
            String msg = String.format("%s is already mapped to %s under this mapper", key, value);
            throw new IllegalArgumentException(msg);
        }
    }
    
}
