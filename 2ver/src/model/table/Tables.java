package model.table;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import model.mapper.Mappers;
import model.mapper.MutableMultiMapper;
import model.mapper.MutableSingleMapper;
import model.presence.One;
import model.presence.Some;
import model.util.UUIDs;

public final class Tables {
    public static final One<BlankCore> BLANK_CORE = One.of(new BlankCore());
    
    private Tables() {}
    
    
    private static abstract class AbstractTable<V> implements Table<V> {
        private final UUID tableID;
        
        private AbstractTable(UUID tableID) {
            this.tableID = tableID;
        }
        
        public UUID getTableKey() {
            return tableID;
        }
    }
    
    public static class BaseTable extends AbstractTable<BlankCore> {
        private final Set<UUID> domain;
        
        private BaseTable(UUID tableID, Set<UUID> domain) {
            super(tableID);
            this.domain = domain;
        }
        
        public Set<Table<?>> getBaseTables() {
            return Collections.emptySet();
        }
        
        public Set<UUID> keys() {
            return Collections.unmodifiableSet(domain);
        }
        
        public Some<BlankCore> get(UUID key) {
            Tables.requireKeyPresence(this, key);
            return BLANK_CORE;
        }
        
        public UUID add() {
            UUID rowKey = UUID.randomUUID();
            domain.add(rowKey);
            
            return rowKey;
        }
        
        public void remove(UUID rowKey) {
            Objects.requireNonNull(rowKey);
            Tables.requireKeyPresence(this, rowKey);
            
            domain.remove(rowKey);
        }
    }
    
    public static class MKMVTable<V> extends AbstractTable<V> {
        // An unmodifiable set of base tables.
        private final Set<Table<?>> baseTables;
        private final MutableMultiMapper<UUID, V> mapper;
        
        private MKMVTable(UUID tableID, Set<Table<?>> baseTables, MutableMultiMapper<UUID, V> mapper) {
            super(tableID);
            this.baseTables = baseTables;
            this.mapper = mapper;
        }
        
        public Set<Table<?>> getBaseTables() {
            return baseTables;
        }
        
        public Set<UUID> keys() {
            return mapper.keys();
        }
        
        public Some<V> get(UUID rowKey) {
            Tables.requireKeyPresence(this, rowKey);
            
            return mapper.get(rowKey).certainly();
        }
        
        public UUID add(Set<UUID> keys, V value) {
            Objects.requireNonNull(keys);
            if (!Table.verifyKeys(baseTables, keys)) {
                String msg = String.format("%s is an invalid combination of keys for this table", keys);
                throw new IllegalArgumentException(msg);
            }
            
            UUID rowKey = keys.stream().reduce((uuid1, uuid2) -> UUIDs.xorUUIDs(uuid1, uuid2)).get();
            Tables.requireNoAssociation(this, rowKey, value);
            mapper.map(rowKey, value);
            return rowKey;
        }
        
        public void replace(UUID rowKey, V oldValue, V newValue) {
            Objects.requireNonNull(rowKey);
            Tables.requireAssociation(this, rowKey, oldValue);
            Tables.requireNoAssociation(this, rowKey, newValue);
            
            mapper.unmap(rowKey, oldValue);
            mapper.map(rowKey, newValue);
        }
        
        public void remove(UUID rowKey, V value) {
            Objects.requireNonNull(rowKey);
            Tables.requireAssociation(this, rowKey, value);
            
            mapper.unmap(rowKey, value);
        }
        
        public void remove(UUID rowKey) {
            Objects.requireNonNull(rowKey);
            Tables.requireKeyPresence(this, rowKey);
            
            mapper.unmapAll(rowKey);
        }
    }
    
    public static class SKSVTable<V> extends AbstractTable<V> {
        private final Table<?> baseTable;
        private final MutableSingleMapper<UUID, V> mapper;
        
        private SKSVTable(UUID tableID, Table<?> baseTable, MutableSingleMapper<UUID, V> mapper) {
            super(tableID);
            this.baseTable = baseTable;
            this.mapper = mapper;
        }
        
        public Set<Table<?>> getBaseTables() {
            return Set.of(baseTable);
        }
        
        public Set<UUID> keys() {
            return mapper.keys();
        }
        
        public One<V> get(UUID key) {
            return mapper.get(key).certainly();
        }
        
        public UUID add(UUID key, V value) {
            Objects.requireNonNull(key);
            if (!baseTable.keys().contains(key)) {
                String msg = String.format("%s is not a valid key from the base table (%s)",
                        key, baseTable.getTableKey());
                throw new IllegalArgumentException(msg);
            }
            
            UUID tableKey = UUIDs.xorUUIDs(getTableKey(), key);
            Tables.requireKeyAbsence(this, tableKey);
            mapper.map(key, value);
            return tableKey;
        }
        
        public void replace(UUID rowKey, V newValue) {
            Objects.requireNonNull(rowKey);
            Tables.requireKeyPresence(this, rowKey);
            Tables.requireNoAssociation(this, rowKey, newValue);
            
            mapper.unmap(rowKey);
            mapper.map(rowKey, newValue);
        }
        
        public void remove(UUID rowKey) {
            Objects.requireNonNull(rowKey);
            Tables.requireKeyPresence(this, rowKey);
            
            mapper.unmap(rowKey);
        }
    }
    
    public static <T> BaseTable baseTable() {
        return new BaseTable(UUID.randomUUID(), new HashSet<>());
    }
    
    public static <T> MKMVTable<T> multiKeyMultiValueTable(Set<Table<?>> baseDomains) {
        Objects.requireNonNull(baseDomains);
        return new MKMVTable<T>(UUID.randomUUID(), Set.copyOf(baseDomains), Mappers.multiMapper());
    }
    
    public static <T> SKSVTable<T> singleKeySingleValueTable(Table<?> baseDomain) {
        Objects.requireNonNull(baseDomain);
        return new SKSVTable<T>(UUID.randomUUID(), baseDomain, Mappers.singleMapper());
    }
    
    public static <T> void requireKeyPresence(Table<?> table, UUID key) {
        if (!table.keys().contains(key)) {
            String msg = String.format("The table (%s) does not contain %s",
                    table, key);
            throw new IllegalArgumentException(msg);
        }
    }
    
    public static <T> void requireKeyAbsence(Table<?> table, UUID key) {
        if (table.keys().contains(key)) {
            String msg = String.format("The table (%s) already contains %s",
                    table, key);
            throw new IllegalArgumentException(msg);
        }
    }
    
    public static <T> void requireAssociation(Table<T> table, UUID key, T value) {
        if (table.keys().contains(key) && !table.get(key).has(value)) {
            String msg = String.format("%s is not associated with %s in this table (%s)",
                    key, value, table.getTableKey());
            throw new IllegalArgumentException(msg);
        }
    }
    
    public static <T> void requireNoAssociation(Table<T> table, UUID key, T value) {
        if (table.keys().contains(key) && table.get(key).has(value)) {
            String msg = String.format("%s is associated with %s in this table (%s)",
                    key, value, table.getTableKey());
            throw new IllegalArgumentException(msg);
        }
    }
}
