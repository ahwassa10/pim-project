package model.table;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import model.mapper.Mapper;
import model.presence.MaybeSome;
import model.presence.None;
import model.presence.Some;
import model.util.UUIDs;

public final class ExisTable {
    private final UUID tableID;
    private final Set<UUID> keys;
    
    
    public ExisTable() {
        this.tableID = UUID.randomUUID();
        this.keys = new HashSet<>();
    }
    
    public UUID getTableID() {
        return tableID;
    }
    
    public boolean contains(UUID combinedTableKey) {
        return keys.contains(combinedTableKey);
    }
    
    public Mapper<UUID, ?> view() {
        return new Mapper<UUID, Object>() {
            public MaybeSome<Object> get(UUID key) {
                return None.of();
            }
            
            public MaybeSome<UUID> keys() {
                if (keys.size() == 0) {
                    return None.of();
                } else {
                    return Some.of(keys);
                }
            }
        };
    }
    
    public UUID put(UUID key) {
        Objects.requireNonNull(key);
        
        if (keys.contains(key)) {
            String msg = String.format("%s is already contained in this table", key);
            throw new IllegalArgumentException(msg);
        }
        
        UUID combinedTableKey = UUIDs.xorUUIDs(key, tableID);
        keys.add(combinedTableKey);
        return combinedTableKey;
    }
    
    public void remove(UUID combinedTableKey) {
        Objects.requireNonNull(combinedTableKey);
        
        if (!keys.contains(combinedTableKey)) {
            String msg = String.format("%s is not contained in this table", combinedTableKey);
            throw new IllegalArgumentException(msg);
        }
        
        keys.remove(combinedTableKey);
    }
}
