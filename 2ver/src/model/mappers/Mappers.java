package model.mappers;

public final class Mappers {
    private Mappers() {}
    
    public static <K> K requireValues(Mapper<K, ?> mapper, K key) {
        if (!mapper.hasValues(key)) {
            String msg = String.format("%s has not been mapped to any values under this mapper", key);
            throw new IllegalArgumentException(msg);
        }
        return key;
    }
    
    public static <K> K requireNoValues(Mapper<K, ?> mapper, K key) {
        if (mapper.hasValues(key)) {
            String msg = String.format("%s already maps to a value under this mapper", key);
            throw new IllegalArgumentException(msg);
        }
        return key;
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
    
    public static <K, V> void requireCanMap(MutableMapper<K,V> mapper, K key, V value) {
        if (!mapper.canMap(key, value)) {
            String msg = String.format("%s cannot be mapped to %s under this mapper", key, value);
            throw new IllegalArgumentException(msg);
        }
    }
}
