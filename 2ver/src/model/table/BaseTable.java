package model.table;

import java.util.Set;
import java.util.UUID;

public interface BaseTable<T> {
    UUID getTableID();
    BaseTable<?> getBaseTable();
    
    Set<UUID> keys();
    T get(UUID key);
    Drop asDrop(UUID key);
}
