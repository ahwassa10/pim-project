package model.mapper;

import model.presence.MaybeSome;

public interface Mapper<K, V> {
    MaybeSome<K> keys();
    MaybeSome<V> get(K key);
}
