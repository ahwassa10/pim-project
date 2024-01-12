package model.memtable;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import model.table.BaseTable;
import model.table.Drop;
import model.table.SubTable;
import model.table.Table;

abstract class AbstractSubsequentTable<T> extends AbstractBaseTable<T> implements SubTable<T> {
    final BaseTable<?> baseTable;
    
    AbstractSubsequentTable(UUID tableID, Map<UUID, BaseTable<?>> subTables, BaseTable<?> baseTable) {
        super(tableID, subTables);
        this.baseTable = baseTable;
    }
    
    public Drop asDrop(UUID key) {
        Objects.requireNonNull(key);
        AbstractBaseTable.requireKeyPresence(this, key);
        
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
    
    public Table<?> getBaseTable() {
        return baseTable;
    }
}
