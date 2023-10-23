package model.attributive.specification;

public interface Mapper<K, V> {
    View<K, V> forward();
    
    View<V, K> backward();
    
    void map(K key, V value);
    
    void unmap(K key, V value);
    
    void delete(K key);
    
    void forget(V value);
}
