package model.newtables;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import model.mapper.Mappers;

public final class SVTable<T> extends AbstractSVTable<T> {
    private final AbstractTable<?> baseTable;
    
    SVTable(UUID tableID, AbstractTable<?> baseTable) {
        super(tableID, new HashMap<>(), Mappers.singleMapper());
        this.baseTable = baseTable;
    }
    
    public void add(UUID key, T core) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(core);
        AbstractTable.requireKeyPresence(baseTable, key);
        AbstractTable.requireKeyAbsence(this, key);
        AbstractTable.requireUniqueKey(this, key);
        
        addKeyValue(key, core);
    }
    
    public Drop asDrop(UUID key) {
        Objects.requireNonNull(key);
        AbstractTable.requireKeyPresence(this, key);
        
        return new Drop() {
            public UUID getKey() {
                return key;
            }
            
            public UUID getTableID() {
                return SVTable.this.getTableID();
            }
            
            public Object getCore() {
                return SVTable.this.get(key);
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