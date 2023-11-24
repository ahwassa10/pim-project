package model.mappers.specification;

public interface MutableMapper<K, V> extends Mapper<K, V> {
    boolean canMap(K key, V value);
    void map(K key, V value);
    
    void unmap(K key, V value);
    void unmapAll(K key);
}
