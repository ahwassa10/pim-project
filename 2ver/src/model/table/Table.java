package model.table;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import model.mapper.Mapper;
import model.mapper.Mappers;
import model.mapper.MutableMapper;
import model.presence.Some;
import model.table.specification.MKMVTable;
import model.table.specification.Tables;
import model.util.UUIDs;

public final class Table<T> implements MKMVTable<T> {
    private final UUID tableID;
    private final Some<Table<?>> keyTypes;
    private final MutableMapper<UUID, T> mapper;
    
    public Table(Some<Table<?>> keyTypes) {
        Objects.requireNonNull(keyTypes);
        
        this.tableID = UUID.randomUUID();
        this.keyTypes = keyTypes;
        this.mapper = Mappers.multiMapper();
    }
    
    public UUID getTableID() {
        return tableID;
    }
    
    public boolean contains(UUID combinedTableKey) {
        return mapper.get(combinedTableKey).hasAny();
    }
    
    public Mapper<UUID, T> view() {
        return mapper;
    }
    
    private boolean verifyKeys(Some<UUID> keys) {
        if (keyTypes.count() != keys.count()) {
            return false;
        }
        Set<Table<?>> tables = new HashSet<>(keyTypes.asSet());
        
        for (UUID key : keys.asSet()) {
            for (Table<?> table : keyTypes.asSet()) {
                if (table.contains(key)) {
                    tables.remove(table);
                }
            }
        }
        
        return tables.size() == 0;
    }
    
    public UUID put(Some<UUID> keys, T value) {
        Objects.requireNonNull(keys);
        
        if (!verifyKeys(keys)) {
            String msg = String.format("%s is an invalid combination of keys for this table", keys);
            throw new IllegalArgumentException(msg);
        }
        
        UUID combinedKey = keys.stream().reduce((uuid1, uuid2) -> UUIDs.xorUUIDs(uuid1, uuid2)).get();
        UUID combinedTableKey = UUIDs.xorUUIDs(combinedKey, tableID);
        
        Tables.requireNoAssociation(this, combinedTableKey, value);
        
        mapper.map(combinedTableKey, value);
        return combinedTableKey;
    }
    
    public void replace(UUID combinedTableKey, T oldValue, T newValue) {
        Objects.requireNonNull(combinedTableKey);
        
        Tables.requireNoAssociation(this, combinedTableKey, newValue);
        Tables.requireAssociation(this, combinedTableKey, oldValue);
        
        mapper.unmap(combinedTableKey, oldValue);
        mapper.map(combinedTableKey, newValue);
    }
    
    public void remove(UUID combinedTableKey, T value) {
        Objects.requireNonNull(combinedTableKey);
        
        Tables.requireAssociation(this, combinedTableKey, value);
        
        mapper.unmap(combinedTableKey, value);
    }
    
    public void remove(UUID combinedTableKey) {
        Objects.requireNonNull(combinedTableKey);
        
        Tables.requireAnyAssociation(this, combinedTableKey);
        
        mapper.unmapAll(combinedTableKey);
    }
    
}
