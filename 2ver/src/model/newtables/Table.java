package model.newtables;

import java.util.Set;
import java.util.UUID;

public interface Table<T> {
    UUID getTableID();
    Set<? extends Table<?>> getSubsequentTables();
    
    Set<UUID> keys();
    T get(UUID key);
    Drop asDrop(UUID key);
}
