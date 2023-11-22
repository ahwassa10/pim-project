package model.mappers.specification;

import java.util.Iterator;
import java.util.Set;

public interface Mapper<K, V> {
    boolean hasValues(K key);
    V anyValue(K key);
    Set<V> getValues(K key);
    Iterator<V> iterateValues(K key);
    void unmapAll(K key);
    
    boolean canMap(K key, V value);
    void map(K key, V value);
    
    boolean hasMapping(K key, V value);
    void unmap(K key, V value);
}
