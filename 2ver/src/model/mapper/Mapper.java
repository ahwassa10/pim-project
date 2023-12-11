package model.mapper;

import java.util.Iterator;
import java.util.Set;

public interface Mapper<K, V> {
    boolean hasMapping(K key, V value);
    
    int countValues(K key);
    Iterator<V> iterateValues(K key);
    
    boolean hasKey(K key);
    V anyValue(K key);
    Set<V> getValues(K key);
}
