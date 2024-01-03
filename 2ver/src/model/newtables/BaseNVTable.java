package model.newtables;

import java.util.HashSet;
import java.util.UUID;

public final class BaseNVTable extends AbstractNVTable {
    public BaseNVTable() {
        super(UUID.randomUUID(), new HashSet<>(), new HashSet<>());
    }
    
    public UUID add() {
        UUID key = UUID.randomUUID();
        addKey(key);
        return key;
    }
    
    public void delete() {
        for (AbstractTable<?> table : getSubsequentTables()) {
            table.delete();
        }
    }
}
