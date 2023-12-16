package model.table.specification;

import java.util.UUID;

public interface SKMVTable<T> extends MKMVTable<T> {
    UUID put(UUID key, T value);
}
