package model.table;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

import base.mapper.Mappers;
import base.mapper.MutableSingleMapper;

public final class RootSVTable<T> extends AbstractSVTable<T> {
    private RootSVTable(UUID tableID, Map<UUID, Table<?>> subsequentTables, MutableSingleMapper<UUID, T> mapper) {
        super(tableID, subsequentTables, mapper);
    }
    
    public UUID add(T core) {
        Objects.requireNonNull(core);
        
        UUID key = UUID.randomUUID();
        addKeyValue(key, core);
        return key;
    }
    
    public UUID add(Function<UUID, T> function) {
        Objects.requireNonNull(function);
        
        UUID key = UUID.randomUUID();
        T core = function.apply(key);
        Objects.requireNonNull(core);
        addKeyValue(key, core);
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
                return RootSVTable.this.getTableID();
            }
            
            public Object getCore() {
                return RootSVTable.this.get(key);
            }
            
            public boolean hasNextDrop() {
                return false;
            }
            
            public Drop nextDrop() {
                throw new NoSuchElementException();
            }
        };
    }
    
    public static <T> RootSVTable<T> create() {
        return new RootSVTable<>(UUID.randomUUID(), new HashMap<>(), Mappers.singleMapper());
    }
}
