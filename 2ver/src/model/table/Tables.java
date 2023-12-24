package model.table;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import model.mapper.MultiMapper;
import model.mapper.Mappers;
import model.mapper.MutableMultiMapper;
import model.mapper.MutableSingleMapper;
import model.presence.MaybeSome;
import model.presence.One;
import model.presence.Some;
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
        
        public MaybeSome<UUID> keys() {
            return Some.of(domain);
        }
        
        public UUID put() {
            UUID tableKey = UUID.randomUUID();
            domain.add(tableKey);
            
            return tableKey;
        }
        
        public void remove(UUID tableKey) {
            Objects.requireNonNull(tableKey);
            
            if (!domain.contains(tableKey)) {
                String msg = String.format("This table (%s) does not contain %s",
                        getTableID(), tableKey);
                throw new IllegalArgumentException(msg);
            }
            
            domain.remove(tableKey);
        }
    }
    
    public static class MKMVTable<T> extends AbstractTable implements KeyTable, ValueTable<T> {
        private final Some<Table> baseDomains;
        private final MutableMultiMapper<UUID, T> mapper;
        
        private MKMVTable(UUID tableID, Some<Table> baseDomains, MutableMultiMapper<UUID, T> mapper) {
            super(tableID);
            this.baseDomains = baseDomains;
            this.mapper = mapper;
        }
        
        public Some<Table> baseDomains() {
            return baseDomains;
        }
        
        public MaybeSome<UUID> keys() {
            return mapper.keys();
        }
        
        public MultiMapper<UUID, T> view() {
            return mapper;
        }
        
        public UUID put(Some<UUID> keys, T value) {
            Objects.requireNonNull(keys);
            if (!verifyKeys(keys)) {
                String msg = String.format("%s is an invalid combination of keys for this table", keys);
                throw new IllegalArgumentException(msg);
            }
            
            UUID combinedKey = keys.stream().reduce((uuid1, uuid2) -> UUIDs.xorUUIDs(uuid1, uuid2)).get();
            Tables.requireNoAssociation(this, combinedKey, value);
            mapper.map(combinedKey, value);
            
            UUID tableKey = UUIDs.xorUUIDs(getTableID(), combinedKey);
            return tableKey;
        }
        
        public void replace(UUID tableKey, T oldValue, T newValue) {
            Objects.requireNonNull(tableKey);
            
            UUID key = UUIDs.xorUUIDs(getTableID(), tableKey);
            Tables.requireAssociation(this, key, oldValue);
            Tables.requireNoAssociation(this, key, newValue);
            
            mapper.unmap(key, oldValue);
            mapper.map(key, newValue);
        }
        
        public void remove(UUID tableKey, T value) {
            Objects.requireNonNull(tableKey);
            
            UUID key = UUIDs.xorUUIDs(getTableID(), tableKey);
            Tables.requireAssociation(this, key, value);
            
            mapper.unmap(key, value);
        }
        
        public void remove(UUID tableKey) {
            Objects.requireNonNull(tableKey);
            
            UUID key = UUIDs.xorUUIDs(getTableID(), tableKey);
            Tables.requireAnyAssociation(this, key);
            
            mapper.unmapAll(key);
        }
        
    }
    
    public static class SKSVTable<T> extends AbstractTable implements KeyTable, ValueTable<T> {
        private final Table baseDomain;
        private final MutableSingleMapper<UUID, T> mapper;
        
        private SKSVTable(UUID tableID, Table baseDomain, MutableSingleMapper<UUID, T> mapper) {
            super(tableID);
            this.baseDomain = baseDomain;
            this.mapper = mapper;
        }
        
        public Some<Table> baseDomains() {
            return One.of(baseDomain);
        }
        
        public MaybeSome<UUID> keys() {
            return mapper.keys();
        }
        
        public MultiMapper<UUID, T> view() {
            return mapper;
        }
        
        public UUID put(UUID key, T value) {
            Objects.requireNonNull(key);
            
            if (baseDomain.keys().has(key)) {
                String msg = String.format("%s is already contained in this table", key);
                throw new IllegalArgumentException(msg);
            }
            
            mapper.map(key, value);
            return UUIDs.xorUUIDs(getTableID(), key);
        }
        
        public void replace(UUID tableKey, T newValue) {
            Objects.requireNonNull(tableKey);
            
            UUID key = UUIDs.xorUUIDs(getTableID(), tableKey);
            Tables.requireAnyAssociation(this, key);
            Tables.requireNoAssociation(this, key, newValue);
            
            mapper.unmap(key);
            mapper.map(key, newValue);
        }
        
        public void remove(UUID tableKey) {
            Objects.requireNonNull(tableKey);
            
            UUID key = UUIDs.xorUUIDs(getTableID(), tableKey);
            Tables.requireAnyAssociation(this, key);
            
            mapper.unmap(key);
        }
    }
    
    public static <T> MKMVTable<T> multiKeyMultiValueTable(Some<Table> baseDomains) {
        Objects.requireNonNull(baseDomains);
        return new MKMVTable<T>(UUID.randomUUID(), baseDomains, Mappers.multiMapper());
    }
    
    public static <T> SKSVTable<T> singleKeySingleValueTAble(Table baseDomain) {
        Objects.requireNonNull(baseDomain);
        return new SKSVTable<T>(UUID.randomUUID(), baseDomain, Mappers.singleMapper());
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
    
    public static <T> void requireAnyAssociation(ValueTable<T> table, UUID key) {
        if (!table.view().get(key).hasAny()) {
            String msg = String.format("%s is not associated with any value in this table (%s)",
                    key, table.getTableID());
            throw new IllegalArgumentException(msg);
        }
    }
    
    public static <T> void requireNoAssociations(ValueTable<T> table, UUID key) {
        if (table.view().get(key).hasAny()) {
            String msg = String.format("%s is associated with values in this table (%s)",
                    key, table.getTableID());
            throw new IllegalArgumentException(msg);
        }
    }
}
