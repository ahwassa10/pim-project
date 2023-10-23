package model.attributive.specification;

import java.util.Iterator;
import java.util.Set;

public interface View<K, V> {
    boolean hasMappings(K key);
    
    Set<V> getMappings(K key);
    
    Iterator<V> iterateMappings(K key);
}
