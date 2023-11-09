package model.attributive.specification;

public interface BiMapper<K, V> extends Mapper<K, V> {
    BiMapper<V, K> inverse();
}