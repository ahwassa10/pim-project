package model.attributive.implementation;

import model.attributive.specification.BasedMap;
import model.attributive.specification.Mapper;

public final class Mappers {
    private Mappers() {}
   
    public static <V> V requireKeys(Mapper<?, V> mapper, V value) {
        if (!mapper.inverse().hasValues(value)) {
            String msg = String.format("No keys map to %s under this mapper", value);
            throw new IllegalArgumentException(msg);
        }
        return value;
    }
    
    public static <V> V requireNoKeys(Mapper<?, V> mapper, V value) {
        if (mapper.inverse().hasValues(value)) {
            String msg = String.format("A key already maps to %s under this mapper", value);
            throw new IllegalArgumentException(msg);
        }
        return value;
    }
    
    public static <K> K requireValues(BasedMap<K, ?> mapper, K key) {
        if (!mapper.hasValues(key)) {
            String msg = String.format("%s has not been mapped to any values under this mapper", key);
            throw new IllegalArgumentException(msg);
        }
        return key;
    }
    
    public static <K> K requireNoValues(BasedMap<K, ?> mapper, K key) {
        if (mapper.hasValues(key)) {
            String msg = String.format("%s already maps to a value under this mapper", key);
            throw new IllegalArgumentException(msg);
        }
        return key;
    }
    
    public static <K, V> void requireMapping(BasedMap<K, V> mapper, K key, V value) {
        if (!(mapper.hasValues(key) && mapper.getValues(key).contains(value))) {
            String msg = String.format("%s is not mapped to %s under this mapper", key, value);
            throw new IllegalArgumentException(msg);
        }
    }
    
    public static <K, V> void requireNoMapping(BasedMap<K, V> mapper, K key, V value) {
        if (mapper.hasValues(key) && mapper.getValues(key).contains(value)) {
            String msg = String.format("%s is already mapped to %s under this mapper", key, value);
            throw new IllegalArgumentException(msg);
        }
    }
}
