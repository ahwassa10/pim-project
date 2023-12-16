package model.mapper;

import model.presence.Maybe;

public interface MaybeMapper<K, V> extends Mapper<K, V> {
    Maybe<V> get(K key);
}
