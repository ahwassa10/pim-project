package model.memtable;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import base.mapper.MutableSingleMapper;
import model.table.BaseTable;

public final class SVTable<T> extends AbstractSubsequentTable<T> {
    private final MutableSingleMapper<UUID, T> mapper;
    
    SVTable(UUID tableID, Map<UUID, BaseTable<?>> subTables, BaseTable<?> baseTable, MutableSingleMapper<UUID, T> mapper) {
        super(tableID, subTables, baseTable);
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
    
    
    public void add(UUID key, T core) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(core);
        AbstractBaseTable.requireKeyPresence(baseTable, key);
        AbstractBaseTable.requireKeyAbsence(this, key);
        AbstractBaseTable.requireUniqueKey(this, key);
        
        mapper.map(key, core);
    }
    
    void removeKey(UUID key) {
        mapper.unmap(key);
    }
}