package model.newtables;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface Table<T> {
    UUID getTableID();
    Map<UUID, Table<?>> getSubsequentTables();
    
    Set<UUID> keys();
    T get(UUID key);
    Drop asDrop(UUID key);
    
    void remove(UUID key);
}
