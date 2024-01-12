package model.table;

public interface SubTable<T> extends Table<T> {
    Table<?> getBaseTable();
}
