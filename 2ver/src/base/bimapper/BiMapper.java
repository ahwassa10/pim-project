package base.bimapper;

import base.mapper.MultiMapper;

public interface BiMapper<K, V> extends MultiMapper<K, V> {
    BiMapper<V, K> inverse();
}