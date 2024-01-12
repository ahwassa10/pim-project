package model.memtable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import base.mapper.Mappers;
import model.table.BaseTable;
import model.table.Table;

abstract class AbstractBaseTable<T> implements BaseTable<T> {
    private final UUID tableID;
    private final Map<UUID, BaseTable<?>> subTables;
    
    AbstractBaseTable(UUID tableID, Map<UUID, BaseTable<?>> subTables) {
        this.tableID = tableID;
        this.subTables = subTables;
    }
    
    abstract void removeKey(UUID key);
    
    public UUID getTableID() {
        return tableID;
    }
    
    public Map<UUID, BaseTable<?>> getSubTables() {
        return Collections.unmodifiableMap(subTables);
    }
    
    public Map<UUID, NVTable> getSubNVTables() {
        Map<UUID, NVTable> map = new HashMap<>();
        
        for (UUID tableID : subTables.keySet()) {
            Table<?> table = map.get(tableID);
            if (table instanceof NVTable) {
                map.put(tableID, (NVTable) table);
            }
        }
        return map;
    }
    
    public Map<UUID, SVTable<?>> getSubSVTables() {
        Map<UUID, SVTable<?>> map = new HashMap<>();
        
        for (UUID tableID : subTables.keySet()) {
            Table<?> table = map.get(tableID);
            if (table instanceof SVTable) {
                map.put(tableID, (SVTable<?>) table);
            }
        }
        return map;
    }
   
    public void remove(UUID key) {
        Objects.requireNonNull(key);
        AbstractBaseTable.requireKeyPresence(this, key);
        
        removeKey(key);
        for (UUID subTableID : subTables.keySet()) {
            BaseTable<?> subTable = subTables.get(subTableID);
            
            // Case where the presence of a key in a subtable needs to be deleted.
            if (subTable.keys().contains(key)) {
                subTable.remove(key);
            }
            
            // Case where a subtable needs to be deleted.
            if (key.equals(subTableID)) {
                // This is a cascading delete that removes all subtables from the subtable.
                // copyOf is needed to prevent a concurrent modification exception.
                for (UUID subSubTableID : Set.copyOf(subTable.getSubTables().keySet())) {
                    subTable.remove(subSubTableID);
                }
            }
        }
    }
    
    public <U> SVTable<U> createSVTable(UUID newTableID) {
        Objects.requireNonNull(newTableID);
        AbstractBaseTable.requireKeyPresence(this, newTableID);
        
        SVTable<U> newTable = new SVTable<>(newTableID, new HashMap<>(), this, Mappers.singleMapper());
        subTables.put(newTable.getTableID(), newTable);
        return newTable;
    }
    
    public NVTable createNVTable(UUID newTableID) {
        Objects.requireNonNull(newTableID);
        AbstractBaseTable.requireKeyPresence(this, newTableID);
        
        NVTable newTable = new NVTable(newTableID, new HashMap<>(), this, new HashSet<>());
        subTables.put(newTable.getTableID(), newTable);
        return newTable;
    }

    public static <T> void requireNoAssociation(Table<T> table, UUID key, T core) {
        if (table.keys().contains(key) && table.get(key).equals(core)) {
            String msg = String.format("%s is already mapped to %s in this table (%s)",
                    key, core, table);
            throw new IllegalArgumentException(msg);
        }
    }

    public static <T> void requireAssociation(Table<T> table, UUID key, T core) {
        if (!table.keys().contains(key) || !table.get(key).equals(core)) {
            String msg = String.format("%s is not mapped to %s in this table (%s)",
                    key, core, table);
            throw new IllegalArgumentException(msg);
        }
    }

    public static void requireKeyAbsence(Table<?> table, UUID key) {
        if (table.keys().contains(key)) {
            String msg = String.format("%s is already present in this table (%s)",
                    key, table.getTableID());
            throw new IllegalArgumentException(msg);
        }
    }

    public static void requireKeyPresence(Table<?> table, UUID key) {
        if (!table.keys().contains(key)) {
            String msg = String.format("%s is not present in this table (%s)",
                    key, table.getTableID());
            throw new IllegalArgumentException(msg);
        }
    }
    
    public static void requireUniqueKey(Table<?> table, UUID key) {
        if (table.getTableID().equals(key)) {
            String msg = String.format("%s is the table key of this table (%s)",
                    key, table.getTableID());
            throw new IllegalArgumentException(msg);
        }
    }
}
