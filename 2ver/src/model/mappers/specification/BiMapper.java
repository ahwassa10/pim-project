package model.mappers.specification;

public interface BiMapper<K, V> extends Mapper<K, V> {
    BiMapper<V, K> inverse();
}