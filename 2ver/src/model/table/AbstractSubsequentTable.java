package model.table;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

abstract class AbstractSubsequentTable<T> extends AbstractTable<T> {
    final Table<?> baseTable;
    
    AbstractSubsequentTable(UUID tableID, Map<UUID, Table<?>> subTables, Table<?> baseTable) {
        super(tableID, subTables);
        this.baseTable = baseTable;
    }
    
    public Drop asDrop(UUID key) {
        Objects.requireNonNull(key);
        AbstractTable.requireKeyPresence(this, key);
        
        return new Drop() {
            public UUID getKey() {
                return key;
            }
            
            public UUID getTableID() {
                return AbstractSubsequentTable.this.getTableID();
            }
            
            public Object getCore() {
                return AbstractSubsequentTable.this.get(key);
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
