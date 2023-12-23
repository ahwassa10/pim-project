package model.table;

import java.util.UUID;

import model.mapper.MultiMapper;
import model.presence.Some;

public interface ValueTable<T> extends KeyDomain {
    Some<KeyDomain> baseDomains();
    MultiMapper<UUID, T> view();
    UUID put(Some<UUID> keys, T value);
    void replace(UUID combinedTableKey, T oldValue, T newValue);
    void remove(UUID combinedTableKey, T value);
    void remove(UUID combinedTableKey);
}
