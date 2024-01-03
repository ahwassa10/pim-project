package model.newtables;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

public final class NVTable extends AbstractNVTable {
    private final AbstractTable<?> baseTable;
    
    NVTable(AbstractTable<?> baseTable) {
        super(UUID.randomUUID(), new HashSet<>(), new HashSet<>());
        this.baseTable = baseTable;
    }
    
    public void add(UUID key) {
        Objects.requireNonNull(key);
        AbstractTable.requireKeyPresence(baseTable, key);
        AbstractTable.requireKeyAbsence(this, key);
        
        addKey(key);
    }
    
    public void delete() {
        for (AbstractTable<?> table : getSubsequentTables()) {
            table.delete();
        }
        baseTable.removeSubsquentTable(this);
    }
}
