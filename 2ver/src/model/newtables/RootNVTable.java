package model.newtables;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public final class RootNVTable extends AbstractNVTable {
    private RootNVTable(UUID tableID, Map<UUID, Table<?>> subsequentTables, Set<UUID> domain) {
        super(UUID.randomUUID(), subsequentTables, domain);
    }
    
    public UUID add() {
        UUID key = UUID.randomUUID();
        addKey(key);
        return key;
    }
    
    public Drop asDrop(UUID key) {
        Objects.requireNonNull(key);
        AbstractTable.requireKeyPresence(this, key);
        
        return new Drop() {
            public UUID getKey() {
                return key;
            }
            
            public UUID getTableID() {
                return RootNVTable.this.getTableID();
            }
            
            public Object getCore() {
                return RootNVTable.this.get(key);
            }
            
            public boolean hasNextDrop() {
                return false;
            }
            
            public Drop nextDrop() {
                throw new NoSuchElementException();
            }
        };
    }
    
    public static RootNVTable create() {
        return new RootNVTable(UUID.randomUUID(), new HashMap<>(), new HashSet<>());
    }
}
