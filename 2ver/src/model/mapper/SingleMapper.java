package model.mapper;

import model.presence.Maybe;

public interface SingleMapper<K, V> extends MultiMapper<K, V> {
    Maybe<V> get(K key);
}
