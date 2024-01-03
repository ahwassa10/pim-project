package model.newtables;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

abstract class AbstractTable<T> implements Table<T> {
    private final UUID tableID;
    private final Set<AbstractTable<?>> subsequentTables;
    
    AbstractTable(UUID tableID, Set<AbstractTable<?>> subsequentTables) {
        this.tableID = tableID;
        this.subsequentTables = subsequentTables;
    }
    
    abstract void removeKey(UUID key);
    
    void removeSubsquentTable(AbstractTable<?> table) {
        subsequentTables.remove(table);
    }
    
    public UUID getTableID() {
        return tableID;
    }
    
    public Set<AbstractTable<?>> getSubsequentTables() {
        return Collections.unmodifiableSet(subsequentTables);
    }
   
    public void remove(UUID key) {
        Objects.requireNonNull(key);
        AbstractTable.requireKeyPresence(this, key);
        
        removeKey(key);
        for (AbstractTable<?> table : subsequentTables) {
            if (table.keys().contains(key)) {
                table.remove(key);
            }
        }
    }
    
    public abstract void delete();
    
    public <U> SVTable<U> createSVTable(UUID newTableID) {
        Objects.requireNonNull(newTableID);
        AbstractTable.requireKeyPresence(this, newTableID);
        
        SVTable<U> newTable = new SVTable<>(this);
        subsequentTables.add(newTable);
        return newTable;
    }
    
    public NVTable createNVTable(UUID newTableID) {
        Objects.requireNonNull(newTableID);
        AbstractTable.requireKeyPresence(this, newTableID);
        
        NVTable newTable = new NVTable(this);
        subsequentTables.add(newTable);
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
}
