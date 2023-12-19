package model.table.spec;

import java.util.Objects;
import java.util.UUID;

import model.presence.MaybeSome;
import model.util.UUIDs;

public interface KeyDomain {
    UUID getDomainID();
    MaybeSome<KeyDomain> baseDomains();
    MaybeSome<UUID> keys();
    
    default UUID compute(UUID other) {
        Objects.requireNonNull(other);
        return UUIDs.xorUUIDs(other, getDomainID());
    }
}
