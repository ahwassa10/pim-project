package model.table;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import model.mapper.MultiMapper;
import model.mapper.Mappers;
import model.mapper.MutableMultiMapper;
import model.mapper.MutableSingleMapper;
import model.util.UUIDs;

public final class Tables {
    private Tables() {}
    
    private static abstract class AbstractTable implements Table {
        private final UUID tableID;
        
        private AbstractTable(UUID tableID) {
            this.tableID = tableID;
        }
        
        public UUID getTableID() {
            return tableID;
        }
    }
    
    public static class BaseTable extends AbstractTable implements Table {
        private final Set<UUID> domain;
        
        private BaseTable(UUID tableID, Set<UUID> domain) {
            super(tableID);
            this.domain = domain;
        }
        
        public Set<UUID> keys() {
            return Collections.unmodifiableSet(domain);
        }
        
        public UUID put() {
            UUID tableKey = UUID.randomUUID();
            domain.add(tableKey);
            
            return tableKey;
        }
        
        public void remove(UUID tableKey) {
            Objects.requireNonNull(tableKey);
            Tables.requireKeyPresence(this, tableKey);
            
            domain.remove(tableKey);
        }
    }
    
    public static class MKMVTable<T> extends AbstractTable implements KeyTable, ValueTable<T> {
        private final Set<Table> baseTables;
        private final MutableMultiMapper<UUID, T> mapper;
        
        private MKMVTable(UUID tableID, Set<Table> baseTables, MutableMultiMapper<UUID, T> mapper) {
            super(tableID);
            this.baseTables = baseTables;
            this.mapper = mapper;
        }
        
        public Set<Table> baseTables() {
            return Collections.unmodifiableSet(baseTables);
        }
        
        public Set<UUID> keys() {
            return mapper.keys();
        }
        
        public MultiMapper<UUID, T> view() {
            return mapper;
        }
        
        public UUID put(Set<UUID> keys, T value) {
            Objects.requireNonNull(keys);
            if (!KeyTable.verifyKeys(baseTables, keys)) {
                String msg = String.format("%s is an invalid combination of keys for this table", keys);
                throw new IllegalArgumentException(msg);
            }
            
            UUID combinedKey = keys.stream().reduce((uuid1, uuid2) -> UUIDs.xorUUIDs(uuid1, uuid2)).get();
            UUID tableKey = UUIDs.xorUUIDs(getTableID(), combinedKey);
            Tables.requireNoAssociation(this, tableKey, value);
            mapper.map(tableKey, value);
            
            return tableKey;
        }
        
        public void replace(UUID tableKey, T oldValue, T newValue) {
            Objects.requireNonNull(tableKey);
            Tables.requireAssociation(this, tableKey, oldValue);
            Tables.requireNoAssociation(this, tableKey, newValue);
            
            mapper.unmap(tableKey, oldValue);
            mapper.map(tableKey, newValue);
        }
        
        public void remove(UUID tableKey, T value) {
            Objects.requireNonNull(tableKey);
            Tables.requireAssociation(this, tableKey, value);
            
            mapper.unmap(tableKey, value);
        }
        
        public void remove(UUID tableKey) {
            Objects.requireNonNull(tableKey);
            Tables.requireKeyPresence(this, tableKey);
            
            mapper.unmapAll(tableKey);
        }
    }
    
    public static class SKSVTable<T> extends AbstractTable implements KeyTable, ValueTable<T> {
        private final Table baseTable;
        private final MutableSingleMapper<UUID, T> mapper;
        
        private SKSVTable(UUID tableID, Table baseTable, MutableSingleMapper<UUID, T> mapper) {
            super(tableID);
            this.baseTable = baseTable;
            this.mapper = mapper;
        }
        
        public Set<Table> baseTables() {
            return Set.of(baseTable);
        }
        
        public Set<UUID> keys() {
            return mapper.keys();
        }
        
        public MultiMapper<UUID, T> view() {
            return mapper;
        }
        
        public UUID put(UUID key, T value) {
            Objects.requireNonNull(key);
            
            UUID tableKey = UUIDs.xorUUIDs(getTableID(), key);
            Tables.requireKeyAbsence(this, tableKey);
            
            mapper.map(key, value);
            return tableKey;
        }
        
        public void replace(UUID tableKey, T newValue) {
            Objects.requireNonNull(tableKey);
            Tables.requireKeyPresence(this, tableKey);
            Tables.requireNoAssociation(this, tableKey, newValue);
            
            mapper.unmap(tableKey);
            mapper.map(tableKey, newValue);
        }
        
        public void remove(UUID tableKey) {
            Objects.requireNonNull(tableKey);
            Tables.requireKeyPresence(this, tableKey);
            
            mapper.unmap(tableKey);
        }
    }
    
    public static <T> BaseTable baseTable() {
        return new BaseTable(UUID.randomUUID(), new HashSet<>());
    }
    
    public static <T> MKMVTable<T> multiKeyMultiValueTable(Set<Table> baseDomains) {
        Objects.requireNonNull(baseDomains);
        return new MKMVTable<T>(UUID.randomUUID(), Set.copyOf(baseDomains), Mappers.multiMapper());
    }
    
    public static <T> SKSVTable<T> singleKeySingleValueTable(Table baseDomain) {
        Objects.requireNonNull(baseDomain);
        return new SKSVTable<T>(UUID.randomUUID(), baseDomain, Mappers.singleMapper());
    }
    
    public static <T> void requireKeyPresence(Table table, UUID key) {
        if (!table.keys().contains(key)) {
            String msg = String.format("The table (%s) does not contain %s",
                    table, key);
            throw new IllegalArgumentException(msg);
        }
    }
    
    public static <T> void requireKeyAbsence(Table table, UUID key) {
        if (table.keys().contains(key)) {
            String msg = String.format("The table (%s) already contains %s",
                    table, key);
            throw new IllegalArgumentException(msg);
        }
    }
    
    public static <T> void requireAssociation(ValueTable<T> table, UUID key, T value) {
        if (!table.view().get(key).has(value)) {
            String msg = String.format("%s is not associated with %s in this table (%s)",
                    key, value, table.getTableID());
            throw new IllegalArgumentException(msg);
        }
    }
    
    public static <T> void requireNoAssociation(ValueTable<T> table, UUID key, T value) {
        if (table.view().get(key).has(value)) {
            String msg = String.format("%s is associated with %s in this table (%s)",
                    key, value, table.getTableID());
            throw new IllegalArgumentException(msg);
        }
    }
}
