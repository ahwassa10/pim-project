package model.table.specification;

import java.util.UUID;

import model.mapper.Mapper;
import model.presence.Some;

public interface MKMVTable<T> {
    UUID getTableID();
    boolean contains(UUID combinedTableKey);
    Mapper<UUID, T> view();
    
    UUID put(Some<UUID> keys, T value);
    void replace(UUID combinedTableKey, T oldValue, T newValue);
    void remove(UUID combinedTableKey, T value);
    void remove(UUID combinedTableKey);
}
