package model.attributive.specification;

import java.util.Iterator;
import java.util.Set;

public interface Mapper<K, V> {
    boolean hasValues(K key);
    
    Set<V> getValues(K key);
    
    Iterator<V> iterateValues(K key);
    
    void map(K key, V value);
    
    void unmap(K key, V value);
    
    void unmapAll(K key);
    
    Mapper<V, K> inverse();
}