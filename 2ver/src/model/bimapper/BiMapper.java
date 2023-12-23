package model.bimapper;

import model.mapper.MultiMapper;

public interface BiMapper<K, V> extends MultiMapper<K, V> {
    BiMapper<V, K> inverse();
}