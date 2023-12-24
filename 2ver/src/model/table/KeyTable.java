package model.table;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public interface KeyTable extends Table {
    Set<Table> baseTables();
    
    static boolean verifyKeys(Set<Table> tables, Set<UUID> keys) {
        if (tables.size() == 0) {
            return keys.size() == 0;
        } else if (tables.size() != keys.size()) {
            return false;
        } else {
            Set<Table> domains = new HashSet<>(tables);
            
            for (UUID key : keys) {
                for (Table domain : tables) {
                    if (domain.keys().contains(key)) {
                        domains.remove(domain);
                    }
                }
            }
            
            return domains.size() == 0;
            
        }
    }
}
