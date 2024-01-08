package base.mapper;

import base.presence.Maybe;

public interface SingleMapper<K, V> extends MultiMapper<K, V> {
    Maybe<V> get(K key);
}
