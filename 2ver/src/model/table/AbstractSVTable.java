package model.table;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import base.mapper.MutableSingleMapper;

abstract class AbstractSVTable<T> extends AbstractTable<T> {
    private final MutableSingleMapper<UUID, T> mapper;
    
    AbstractSVTable(UUID tableID, Map<UUID, Table<?>> subsequentTables, MutableSingleMapper<UUID, T> mapper) {
        super(tableID, subsequentTables);
        this.mapper = mapper;
    }
    
    void addKeyValue(UUID key, T value) {
        mapper.map(key, value);
    }
    
    void removeKey(UUID key) {
        mapper.unmap(key);
    }
    
    public Set<UUID> keys() {
        return mapper.keys();
    }
    
    public T get(UUID key) {
        AbstractTable.requireKeyPresence(this, key);
        return mapper.get(key).certainly().any();
    }
    
    public void replace(UUID key, T newCore) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(newCore);
        AbstractTable.requireKeyPresence(this, key);
        AbstractTable.requireNoAssociation(this, key, newCore);
        
        mapper.unmap(key);
        mapper.map(key, newCore);
    }
}
