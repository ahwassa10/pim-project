package model.newtables;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

import model.mapper.Mappers;

public final class BaseSVTable<T> extends AbstractSVTable<T> {
    public BaseSVTable() {
        super(UUID.randomUUID(), new HashSet<>(), Mappers.singleMapper());
    }
    
    public UUID add(T core) {
        Objects.requireNonNull(core);
        
        UUID key = UUID.randomUUID();
        addKeyValue(key, core);
        return key;
    }
    
    public UUID add(Function<UUID, T> function) {
        Objects.requireNonNull(function);
        
        UUID key = UUID.randomUUID();
        T core = function.apply(key);
        Objects.requireNonNull(core);
        addKeyValue(key, core);
        return key;
    }
    
    public void delete() {
        for (AbstractTable<?> table : getSubsequentTables()) {
            table.delete();
        }
    }
}
