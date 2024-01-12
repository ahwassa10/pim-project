package model.table;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public final class RootNVTable extends AbstractRootTable<BlankCore> {
    private final Set<UUID> domain;
    
    private RootNVTable(UUID tableID, Map<UUID, Table<?>> subsequentTables, Set<UUID> domain) {
        super(UUID.randomUUID(), subsequentTables);
        this.domain = domain;
    }
    
    public Set<UUID> keys() {
        return Collections.unmodifiableSet(domain);
    }
    
    public BlankCore get(UUID key) {
        AbstractTable.requireKeyPresence(this, key);
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
