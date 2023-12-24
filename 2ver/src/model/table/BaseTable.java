package model.table;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import model.presence.MaybeSome;
import model.presence.Some;
import model.util.UUIDs;

public final class BaseTable implements Table {
    private final UUID domainID;
    private final Set<UUID> domain;
    
    public BaseTable(UUID domainID, Set<UUID> domain) {
        this.domainID = domainID;
        this.domain = domain;
    }
    
    public BaseTable() {
        this(UUID.randomUUID(), new HashSet<>());
    }
    
    public UUID getTableID() {
        return this.domainID;
    }
    
    public MaybeSome<UUID> keys() {
        return Some.of(domain);
    }
    
    public UUID put(UUID key) {
        Objects.requireNonNull(key);
        if (domain.contains(key)) {
            String msg = String.format("%s is already contained in this table", key);
            throw new IllegalArgumentException(msg);
        }
        
        domain.add(key);
        return UUIDs.xorUUIDs(domainID, key);
    }
    
    public void remove(UUID tableKey) {
        Objects.requireNonNull(tableKey);
        
        UUID key = UUIDs.xorUUIDs(domainID, tableKey);
        if (!domain.contains(key)) {
            String msg = String.format("%s is not contained in this table", key);
            throw new IllegalArgumentException(msg);
        }
        
        domain.remove(key);
    }
}
