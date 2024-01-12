package model.table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import base.mapper.Mappers;

abstract class AbstractTable<T> implements Table<T> {
    private final UUID tableID;
    private final Map<UUID, Table<?>> subTables;
    
    AbstractTable(UUID tableID, Map<UUID, Table<?>> subsequentTables) {
        this.tableID = tableID;
        this.subTables = subsequentTables;
    }
    
    abstract void removeKey(UUID key);
    
    public UUID getTableID() {
        return tableID;
    }
    
    public Map<UUID, Table<?>> getSubTables() {
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
        AbstractTable.requireKeyPresence(this, key);
        
        removeKey(key);
        List<UUID> deletedTables = new ArrayList<>();
        for (UUID tableID : subTables.keySet()) {
            Table<?> table = subTables.get(tableID);
            // Case to remove the table.
            if (tableID.equals(key)) {
                // Triggers a cascading delete that removes all subsequent, subsequent tables.
                for (UUID subTableID : table.getSubTables().keySet()) {
                    table.remove(subTableID);
                }
                deletedTables.add(tableID);
            }
            
            // Case to remove regular content in the table.
            if (table.keys().contains(key)) {
                table.remove(key);
            }
        }
        for (UUID tableID : deletedTables) {
            subTables.remove(tableID);
        }
    }
    
    public <U> SVTable<U> createSVTable(UUID newTableID) {
        Objects.requireNonNull(newTableID);
        AbstractTable.requireKeyPresence(this, newTableID);
        
        SVTable<U> newTable = new SVTable<>(newTableID, this, Mappers.singleMapper());
        subTables.put(newTable.getTableID(), newTable);
        return newTable;
    }
    
    public NVTable createNVTable(UUID newTableID) {
        Objects.requireNonNull(newTableID);
        AbstractTable.requireKeyPresence(this, newTableID);
        
        NVTable newTable = new NVTable(newTableID, this, new HashSet<>());
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
