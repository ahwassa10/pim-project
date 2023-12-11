package model.bimapper;

import model.mapper.Mapper;

public interface BiMapper<K, V> extends Mapper<K, V> {
    BiMapper<V, K> inverse();
}