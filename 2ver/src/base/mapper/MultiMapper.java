package base.mapper;

import java.util.Set;

import base.presence.MaybeSome;

public interface MultiMapper<K, V> {
    Set<K> keys();
    MaybeSome<V> get(K key);
}