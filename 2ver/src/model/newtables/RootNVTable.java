package model.newtables;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class RootNVTable extends AbstractNVTable {
    private RootNVTable(UUID tableID, Set<AbstractTable<?>> subsequentTables, Set<UUID> domain) {
        super(UUID.randomUUID(), subsequentTables, domain);
    }
    
    public UUID add() {
        UUID key = UUID.randomUUID();
        addKey(key);
        return key;
    }
    
    public static RootNVTable create() {
        UUID tableID = UUID.randomUUID();
        Set<UUID> domain = new HashSet<>();
        domain.add(tableID);
        
        return new RootNVTable(tableID, new HashSet<>(), domain);
    }
}
