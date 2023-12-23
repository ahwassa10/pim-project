package model.mapper;

public interface MutableSingleMapper<K, V> extends MutableMultiMapper<K, V>, SingleMapper<K, V> {
    void remap(K key, V value);
    void unmap(K key);
}
