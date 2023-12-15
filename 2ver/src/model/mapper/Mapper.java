package model.mapper;

import java.util.stream.Stream;

import model.presence.MaybeSome;

public interface Mapper<K, V> {
    boolean hasMapping(K key, V value);
    MaybeSome<V> get(K key);
    Stream<K> keyStream();
}
