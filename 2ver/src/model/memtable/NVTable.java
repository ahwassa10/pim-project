package model.memtable;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import model.table.BaseTable;
import model.table.BlankCore;

public final class NVTable extends AbstractSubsequentTable<BlankCore> {
    private final Set<UUID> domain;
    
    NVTable(UUID tableID, Map<UUID, BaseTable<?>> subTables, BaseTable<?> baseTable, Set<UUID> domain) {
        super(tableID, subTables, baseTable);
        this.domain = domain;
    }
    
    public Set<UUID> keys() {
        return Collections.unmodifiableSet(domain);
    }
    
    public BlankCore get(UUID key) {
        Objects.requireNonNull(key);
        AbstractBaseTable.requireKeyPresence(this, key);
        
        return BlankCore.BLANK_CORE;
    }
    
    void removeKey(UUID key) {
        domain.remove(key);
    }
    
    public void add(UUID key) {
        Objects.requireNonNull(key);
        AbstractBaseTable.requireKeyPresence(baseTable, key);
        AbstractBaseTable.requireKeyAbsence(this, key);
        AbstractBaseTable.requireUniqueKey(this, key);
        
        domain.add(key);
    }
}
