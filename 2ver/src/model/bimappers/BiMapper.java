package model.bimappers;

import model.mappers.Mapper;

public interface BiMapper<K, V> extends Mapper<K, V> {
    BiMapper<V, K> inverse();
}