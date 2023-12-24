package model.mapper;

import java.util.Set;

import model.presence.MaybeSome;

public interface MultiMapper<K, V> {
    Set<K> keys();
    MaybeSome<V> get(K key);
}