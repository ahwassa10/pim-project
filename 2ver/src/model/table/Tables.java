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
        
        public One<BlankCore> get(UUID key) {
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
    
    public static class TKNVTable extends AbstractTable<BlankCore> {
        private final Table<?> baseTable;
        private final Table<?> anotherBaseTable;
        private final Set<UUID> domain;
        
        private TKNVTable(UUID tableID, Table<?> baseTable, Table<?> anotherBaseTable, Set<UUID> domain) {
            super(tableID);
            this.baseTable = baseTable;
            this.anotherBaseTable = anotherBaseTable;
            this.domain = domain;
        }
        
        public Set<Table<?>> getBaseTables() {
            return Set.of(baseTable, anotherBaseTable);
        }
        
        public Set<UUID> keys() {
            return Collections.unmodifiableSet(domain);
        }
        
        public One<BlankCore> get(UUID rowKey) {
            Tables.requireKeyPresence(this, rowKey);
            return BLANK_CORE;
        }
        
        public UUID add(UUID key1, UUID key2) {
            Objects.requireNonNull(key1);
            Objects.requireNonNull(key2);
            
            if ((baseTable.keys().contains(key1) && anotherBaseTable.keys().contains(key2))
                    || (baseTable.keys().contains(key2) && anotherBaseTable.keys().contains(key1))) {
                UUID rowKey = UUIDs.xorUUIDs(key1, key2);
                Tables.requireKeyAbsence(this, rowKey);
                domain.add(rowKey);
                return rowKey;
            } else {
                String msg = String.format("%s, %s is an invalid combination of keys for this table",
                        key1, key2);
                throw new IllegalArgumentException(msg);
            }
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
        
        public UUID add(UUID rowKey, V value) {
            Objects.requireNonNull(rowKey);
            if (!baseTable.keys().contains(rowKey)) {
                String msg = String.format("%s is not a valid key from the base table (%s)",
                        rowKey, baseTable.getTableKey());
                throw new IllegalArgumentException(msg);
            }
            
            Tables.requireKeyAbsence(this, rowKey);
            mapper.map(rowKey, value);
            return rowKey;
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
    
    public static class NKSVTable<V> extends AbstractTable<V> {
        private final MutableSingleMapper<UUID, V> mapper;
        
        private NKSVTable(UUID tableID, MutableSingleMapper<UUID, V> mapper) {
            super(tableID);
            this.mapper = mapper;
        }
        
        public Set<Table<?>> getBaseTables() {
            return Collections.emptySet();
        }
        
        public Set<UUID> keys() {
            return mapper.keys();
        }
        
        public One<V> get(UUID key) {
            return mapper.get(key).certainly();
        }
        
        public UUID add(V value) {
            UUID rowKey = UUID.randomUUID();
            mapper.map(rowKey, value);
            return rowKey;
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
    
    public static BaseTable baseTable() {
        return new BaseTable(UUID.randomUUID(), new HashSet<>());
    }
    
    public static TKNVTable twoKeyNoValueTable(Table<?> baseTable, Table<?> anotherBaseTable) {
        Objects.requireNonNull(baseTable);
        Objects.requireNonNull(anotherBaseTable);
        return new TKNVTable(UUID.randomUUID(), baseTable, anotherBaseTable, new HashSet<>());
    }
    
    public static <V> MKMVTable<V> multiKeyMultiValueTable(Set<Table<?>> baseDomains) {
        Objects.requireNonNull(baseDomains);
        return new MKMVTable<V>(UUID.randomUUID(), Set.copyOf(baseDomains), Mappers.multiMapper());
    }
    
    public static <V> SKSVTable<V> singleKeySingleValueTable(Table<?> baseDomain) {
        Objects.requireNonNull(baseDomain);
        return new SKSVTable<V>(UUID.randomUUID(), baseDomain, Mappers.singleMapper());
    }
    
    public static <V> NKSVTable<V> noKeySingleValueTable() {
        return new NKSVTable<V>(UUID.randomUUID(), Mappers.singleMapper());
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
