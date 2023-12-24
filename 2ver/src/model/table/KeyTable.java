package model.table;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import model.presence.Some;

public interface KeyTable extends Table {
    Some<Table> baseDomains();
    
    default boolean verifyKeys(Some<UUID> keys) {
        if (baseDomains().count() != keys.count()) {
            return false;
        }
        Set<Table> domains = new HashSet<>(baseDomains().asSet());
        
        for (UUID key : keys.asSet()) {
            for (Table domain : baseDomains().asSet()) {
                if (domain.keys().has(key)) {
                    domains.remove(domain);
                }
            }
        }
        
        return domains.size() == 0;
    }
}
