package model.memtable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import base.mapper.Mappers;
import base.mapper.MutableSingleMapper;
import model.table.BaseTable;

public final class RootSVTable<T> extends AbstractRootTable<T> {
    private final MutableSingleMapper<UUID, T> mapper;
    
    private RootSVTable(UUID tableID, Map<UUID, BaseTable<?>> subsequentTables, MutableSingleMapper<UUID, T> mapper) {
        super(tableID, subsequentTables);
        this.mapper = mapper;
    }
    
    public Set<UUID> keys() {
        return mapper.keys();
    }
    
    public T get(UUID key) {
        Objects.requireNonNull(key);
        AbstractBaseTable.requireKeyPresence(this, key);
        
        return mapper.get(key).certainly().any();
    }
    
    public UUID add(T core) {
        Objects.requireNonNull(core);
        
        UUID key = UUID.randomUUID();
        mapper.map(key, core);
        return key;
    }
    
    public UUID add(Function<UUID, T> function) {
        Objects.requireNonNull(function);
        
        UUID key = UUID.randomUUID();
        T core = function.apply(key);
        Objects.requireNonNull(core);
        mapper.map(key, core);
        return key;
    }
    
    void removeKey(UUID key) {
        mapper.unmap(key);
    }
    
    public static <T> RootSVTable<T> create() {
        return new RootSVTable<>(UUID.randomUUID(), new HashMap<>(), Mappers.singleMapper());
    }
}
