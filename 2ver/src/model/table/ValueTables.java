package model.table;

import java.util.Objects;
import java.util.UUID;

import model.mapper.MultiMapper;
import model.mapper.Mappers;
import model.mapper.MutableMultiMapper;
import model.mapper.MutableSingleMapper;
import model.presence.MaybeSome;
import model.presence.One;
import model.presence.Some;
import model.util.UUIDs;

public final class ValueTables {
    private ValueTables() {}
    
    public static class MKMVTable<T> implements KeyTable, ValueTable<T> {
        private final UUID domainID;
        private final Some<Table> baseDomains;
        private final MutableMultiMapper<UUID, T> mapper;
        
        private MKMVTable(UUID domainID, Some<Table> baseDomains, MutableMultiMapper<UUID, T> mapper) {
            this.domainID = domainID;
            this.baseDomains = baseDomains;
            this.mapper = mapper;
        }
        
        public UUID getTableID() {
            return this.domainID;
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
            ValueTables.requireNoAssociation(this, combinedKey, value);
            mapper.map(combinedKey, value);
            
            UUID tableKey = UUIDs.xorUUIDs(domainID, combinedKey);
            return tableKey;
        }
        
        public void replace(UUID tableKey, T oldValue, T newValue) {
            Objects.requireNonNull(tableKey);
            
            UUID key = UUIDs.xorUUIDs(domainID, tableKey);
            ValueTables.requireAssociation(this, key, oldValue);
            ValueTables.requireNoAssociation(this, key, newValue);
            
            mapper.unmap(key, oldValue);
            mapper.map(key, newValue);
        }
        
        public void remove(UUID tableKey, T value) {
            Objects.requireNonNull(tableKey);
            
            UUID key = UUIDs.xorUUIDs(domainID, tableKey);
            ValueTables.requireAssociation(this, key, value);
            
            mapper.unmap(key, value);
        }
        
        public void remove(UUID tableKey) {
            Objects.requireNonNull(tableKey);
            
            UUID key = UUIDs.xorUUIDs(domainID, tableKey);
            ValueTables.requireAnyAssociation(this, key);
            
            mapper.unmapAll(key);
        }
        
    }
    
    public static class SKSVTable<T> implements KeyTable, ValueTable<T> {
        private final UUID domainID;
        private final Table baseDomain;
        private final MutableSingleMapper<UUID, T> mapper;
        
        private SKSVTable(UUID domainID, Table baseDomain, MutableSingleMapper<UUID, T> mapper) {
            this.domainID = domainID;
            this.baseDomain = baseDomain;
            this.mapper = mapper;
        }
        
        public UUID getTableID() {
            return this.domainID;
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
            return UUIDs.xorUUIDs(domainID, key);
        }
        
        public void replace(UUID tableKey, T newValue) {
            Objects.requireNonNull(tableKey);
            
            UUID key = UUIDs.xorUUIDs(domainID, tableKey);
            ValueTables.requireAnyAssociation(this, key);
            ValueTables.requireNoAssociation(this, key, newValue);
            
            mapper.unmap(key);
            mapper.map(key, newValue);
        }
        
        public void remove(UUID tableKey) {
            Objects.requireNonNull(tableKey);
            
            UUID key = UUIDs.xorUUIDs(domainID, tableKey);
            ValueTables.requireAnyAssociation(this, key);
            
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
