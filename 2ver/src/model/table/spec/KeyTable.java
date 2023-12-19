package model.table.spec;

import java.util.UUID;

public interface KeyTable extends KeyDomain {
    UUID put(UUID key);
    void remove(UUID key);
}
