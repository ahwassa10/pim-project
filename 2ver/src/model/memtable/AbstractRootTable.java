package model.memtable;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

import model.table.BaseTable;
import model.table.Drop;

abstract class AbstractRootTable<T> extends AbstractBaseTable<T> {
    AbstractRootTable(UUID tableID, Map<UUID, BaseTable<?>> subTables) {
        super(tableID, subTables);
    }
    
    public Drop asDrop(UUID key) {
        Objects.requireNonNull(key);
        AbstractBaseTable.requireKeyPresence(this, key);
        
        return new Drop() {
            public UUID getKey() {
                return key;
            }
            
            public UUID getTableID() {
                return AbstractRootTable.this.getTableID();
            }
            
            public Object getCore() {
                return AbstractRootTable.this.get(key);
            }
            
            public boolean hasNextDrop() {
                return false;
            }
            
            public Drop nextDrop() {
                throw new NoSuchElementException();
            }
        };
    }
}
