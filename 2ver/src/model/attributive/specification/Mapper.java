package model.attributive.specification;

public interface Mapper<K, V> extends BasedMap<K, V> {
    Mapper<V, K> inverse();
}