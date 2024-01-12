package model.table;

import java.util.Map;
import java.util.UUID;

public interface BaseTable<T> extends Table<T> {
    Map<UUID, BaseTable<?>> getSubTables();
    Drop asDrop(UUID key);
    void remove(UUID key);
}
