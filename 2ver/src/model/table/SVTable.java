package model.table;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import base.mapper.MutableSingleMapper;

public final class SVTable<T> extends AbstractSubsequentTable<T> {
    private final MutableSingleMapper<UUID, T> mapper;
    
    SVTable(UUID tableID, Table<?> baseTable, MutableSingleMapper<UUID, T> mapper) {
        super(tableID, new HashMap<>(), baseTable);
        this.mapper = mapper;
    }
    
    public Set<UUID> keys() {
        return mapper.keys();
    }
    
    public T get(UUID key) {
        Objects.requireNonNull(key);
        AbstractTable.requireKeyPresence(this, key);
        
        return mapper.get(key).certainly().any();
    }
    
    
    public void add(UUID key, T core) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(core);
        AbstractTable.requireKeyPresence(baseTable, key);
        AbstractTable.requireKeyAbsence(this, key);
        AbstractTable.requireUniqueKey(this, key);
        
        mapper.map(key, core);
    }
    
    void removeKey(UUID key) {
        mapper.unmap(key);
    }
}