package model.table;

import java.util.Objects;
import java.util.UUID;

import model.presence.MaybeSome;
import model.util.UUIDs;

public interface Table {
    UUID getTableID();
    MaybeSome<UUID> keys();
    
    default UUID compute(UUID other) {
        Objects.requireNonNull(other);
        return UUIDs.xorUUIDs(other, getTableID());
    }
}
