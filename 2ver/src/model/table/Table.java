package model.table;

import java.util.Set;
import java.util.UUID;

public interface Table {
    UUID getTableID();
    Set<UUID> keys();
}
