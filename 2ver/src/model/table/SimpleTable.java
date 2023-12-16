package model.table;

import java.util.Objects;
import java.util.UUID;

import model.mapper.Mappers;
import model.mapper.MaybeMapper;
import model.mapper.MutableMaybeMapper;
import model.presence.Some;
import model.table.specification.SKSVTable;
import model.table.specification.Tables;
import model.util.UUIDs;

public final class SimpleTable<T> implements SKSVTable<T> {
    private final UUID tableID;
    private final Table<?> keyType;
    private final MutableMaybeMapper<UUID, T> mapper;
    
    public SimpleTable(Table<?> keyType) {
        Objects.requireNonNull(keyType);
        
        this.tableID = UUID.randomUUID();
        this.keyType = keyType;
        this.mapper = Mappers.singleMapper();
    }
    
    public UUID getTableID() {
        return tableID;
    }
    
    public boolean contains(UUID combinedTableKey) {
        return mapper.get(combinedTableKey).hasAny();
    }
    
    public MaybeMapper<UUID, T> view() {
        return mapper;
    }
    
    public UUID put(Some<UUID> key, T value) {
        if (key.count() != 1) {
            String msg = String.format("%s is an invalid combination of keys for this table", key);
            throw new IllegalArgumentException(msg);
        }
        
        return put(key.any(), value);
    }
    
    public UUID put(UUID key, T value) {
        Objects.requireNonNull(key);
        
        if (!keyType.contains(key)) {
            String msg = String.format("%s is an invalid key for this table", key);
            throw new IllegalArgumentException(msg);
        }
        
        UUID combinedTableKey = UUIDs.xorUUIDs(key, tableID);
        Tables.requireNoAssociations(this, combinedTableKey);
        mapper.map(combinedTableKey, value);
        return combinedTableKey;
    }
    
    public void replace(UUID combinedTableKey, T oldValue, T newValue) {
        Objects.requireNonNull(combinedTableKey);
        
        Tables.requireAssociation(this, combinedTableKey, oldValue);
        mapper.remap(combinedTableKey, newValue);
    }
    
    public void replace(UUID combinedTableKey, T newValue) {
        Objects.requireNonNull(combinedTableKey);
        
        Tables.requireAnyAssociation(this, combinedTableKey);
        mapper.remap(combinedTableKey, newValue);
    }
    
    public void remove(UUID combinedTableKey, T value) {
        Objects.requireNonNull(combinedTableKey);
        
        Tables.requireAssociation(this, combinedTableKey, value);
        
        mapper.unmap(combinedTableKey);
    }
    
    public void remove(UUID combinedTableKey) {
        Objects.requireNonNull(combinedTableKey);
        
        Tables.requireAnyAssociation(this, combinedTableKey);
        
        mapper.unmap(combinedTableKey);
    }
}
