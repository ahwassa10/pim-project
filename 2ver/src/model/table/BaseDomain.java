package model.table;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import model.presence.MaybeSome;
import model.presence.None;
import model.presence.Some;
import model.util.UUIDs;

public final class BaseDomain implements KeyDomain {
    private final UUID domainID;
    private final Set<UUID> domain;
    
    public BaseDomain(UUID domainID, Set<UUID> domain) {
        this.domainID = domainID;
        this.domain = domain;
    }
    
    public BaseDomain() {
        this(UUID.randomUUID(), new HashSet<>());
    }
    
    public UUID getDomainID() {
        return this.domainID;
    }
    
    public MaybeSome<KeyDomain> baseDomains() {
        return None.of();
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
