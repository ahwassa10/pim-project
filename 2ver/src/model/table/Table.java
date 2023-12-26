package model.table;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import model.presence.Some;

public interface Table<V> {
    UUID getTableKey();
    Set<Table<?>> getBaseTables();
    
    Set<UUID> keys();
    Some<V> get(UUID key);
    
    static boolean verifyKeys(Set<Table<?>> tables, Set<UUID> keys) {
        if (tables.size() != keys.size()) {
            return false;
        } else if (tables.size() == 0 && keys.size() == 0) {
            return true;
        } else {
            Set<Table<?>> tablesRemaining = new HashSet<>(tables);
            
            for (UUID key : keys) {
                for (Table<?> table : tablesRemaining) {
                    if (table.keys().contains(key)) {
                        tablesRemaining.remove(table);
                        break;
                    }
                }
            }
            
            return tablesRemaining.size() == 0;
        }
    }
}
