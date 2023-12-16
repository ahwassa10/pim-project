package model.mapper;

public interface MutableMaybeMapper<K, V> extends MutableMapper<K, V>, MaybeMapper<K, V> {
    void remap(K key, V value);
    void unmap(K key);
}
