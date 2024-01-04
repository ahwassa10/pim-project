package model.newtables;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

abstract class AbstractNVTable extends AbstractTable<BlankCore> {
    private final Set<UUID> domain;
    
    AbstractNVTable(UUID tableID, Map<UUID, Table<?>> subsequentTables, Set<UUID> domain) {
        super(tableID, subsequentTables);
        this.domain = new HashSet<>();
    }
    
    void addKey(UUID key) {
        domain.add(key);
    }
    
    void removeKey(UUID key) {
        domain.remove(key);
    }
    
    public Set<UUID> keys() {
        return Collections.unmodifiableSet(domain);
    }
    
    public BlankCore get(UUID key) {
        AbstractTable.requireKeyPresence(this, key);
        return BlankCore.BLANK_CORE;
    }
}
