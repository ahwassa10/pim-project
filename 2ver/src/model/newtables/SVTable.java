package model.newtables;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

import model.mapper.Mappers;

public final class SVTable<T> extends AbstractSVTable<T> {
    private final AbstractTable<?> baseTable;
    
    SVTable(AbstractTable<?> baseTable) {
        super(UUID.randomUUID(), new HashSet<>(), Mappers.singleMapper());
        this.baseTable = baseTable;
    }
    
    public void add(UUID key, T core) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(core);
        AbstractTable.requireKeyPresence(baseTable, key);
        AbstractTable.requireKeyAbsence(this, key);
        
        addKeyValue(key, core);
    }
    
    public void delete() {
        for (AbstractTable<?> table : getSubsequentTables()) {
            table.delete();
        }
        baseTable.removeSubsquentTable(this);
    }
}