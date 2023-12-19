package model.table;

import java.util.UUID;

import model.presence.Some;

public interface KeyTable extends KeyDomain {
    Some<KeyDomain> baseDomains();
    UUID put(UUID key);
    void remove(UUID key);
}
