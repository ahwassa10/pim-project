package model.table;

import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public final class NVTable extends AbstractSubsequentTable<BlankCore> {
    private final Set<UUID> domain;
    
    NVTable(UUID tableID, Table<?> baseTable, Set<UUID> domain) {
        super(tableID, new HashMap<>(), baseTable);
        this.domain = domain;
    }
    
    public Set<UUID> keys() {
        return Collections.unmodifiableSet(domain);
    }
    
    public BlankCore get(UUID key) {
        Objects.requireNonNull(key);
        AbstractTable.requireKeyPresence(this, key);
        
        return BlankCore.BLANK_CORE;
    }
    
    void removeKey(UUID key) {
        domain.remove(key);
    }
    
    public void add(UUID key) {
        Objects.requireNonNull(key);
        AbstractTable.requireKeyPresence(baseTable, key);
        AbstractTable.requireKeyAbsence(this, key);
        AbstractTable.requireUniqueKey(this, key);
        
        domain.add(key);
    }
}
