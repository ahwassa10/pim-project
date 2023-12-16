package model.mapper;

public interface MutableMapper<K, V> extends Mapper<K, V> {
    boolean canMap(K key, V value);
    void map(K key, V value);
    void remap(K key, V oldValue, V newValue);
    void unmap(K key, V value);
    void unmapAll(K key);
}
