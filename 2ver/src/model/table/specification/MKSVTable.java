package model.table.specification;

import java.util.UUID;

import model.mapper.MaybeMapper;

public interface MKSVTable<T> extends MKMVTable<T> {
    MaybeMapper<UUID, T> view();
    
    void replace(UUID combinedTableKey, T newValue);
}
