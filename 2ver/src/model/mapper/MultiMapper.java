package model.mapper;

import model.presence.MaybeSome;

public interface MultiMapper<K, V> {
    MaybeSome<K> keys();
    MaybeSome<V> get(K key);
}