package model.memtable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import model.table.BaseTable;
import model.table.BlankCore;

public final class RootNVTable extends AbstractRootTable<BlankCore> {
    private final Set<UUID> domain;
    
    private RootNVTable(UUID tableID, Map<UUID, BaseTable<?>> subsequentTables, Set<UUID> domain) {
        super(tableID, subsequentTables);
        this.domain = domain;
    }
    
    public Set<UUID> keys() {
        return Collections.unmodifiableSet(domain);
    }
    
    public BlankCore get(UUID key) {
        AbstractBaseTable.requireKeyPresence(this, key);
        return BlankCore.BLANK_CORE;
    }
    
    public UUID add() {
        UUID key = UUID.randomUUID();
        domain.add(key);
        return key;
    }
    
    void removeKey(UUID key) {
        domain.remove(key);
    }
    
    public static RootNVTable create() {
        return new RootNVTable(UUID.randomUUID(), new HashMap<>(), new HashSet<>());
    }
}
