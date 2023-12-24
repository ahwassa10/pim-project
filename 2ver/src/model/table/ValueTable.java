package model.table;

import java.util.UUID;

import model.mapper.MultiMapper;

public interface ValueTable<T> extends Table {
    MultiMapper<UUID, T> view();
}
