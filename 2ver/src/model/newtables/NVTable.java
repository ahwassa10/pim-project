package model.newtables;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

public final class NVTable extends AbstractNVTable {
    private final AbstractTable<?> baseTable;
    
    NVTable(UUID tableID, AbstractTable<?> baseTable) {
        super(tableID, new HashMap<>(), new HashSet<>());
        this.baseTable = baseTable;
    }
    
    public void add(UUID key) {
        Objects.requireNonNull(key);
        AbstractTable.requireKeyPresence(baseTable, key);
        AbstractTable.requireKeyAbsence(this, key);
        AbstractTable.requireUniqueKey(this, key);
        
        addKey(key);
    }
    
    public Drop asDrop(UUID key) {
        Objects.requireNonNull(key);
        AbstractTable.requireKeyPresence(this, key);
        
        return new Drop() {
            public UUID getKey() {
                return key;
            }
            
            public UUID getTableID() {
                return NVTable.this.getTableID();
            }
            
            public Object getCore() {
                return NVTable.this.get(key);
            }
            
            public boolean hasNextDrop() {
                return true;
            }
            
            public Drop nextDrop() {
                return baseTable.asDrop(key);
            }
        };
    }
}
