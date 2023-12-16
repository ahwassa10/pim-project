package model.table.specification;

import java.util.UUID;

public interface Tables {
    public static <T> void requireAssociation(MKMVTable<T> table, UUID key, T value) {
        if (!table.view().get(key).has(value)) {
            String msg = String.format("%s is not associated with %s in this table (%s)",
                    key, value, table.getTableID());
            throw new IllegalArgumentException(msg);
        }
    }
    
    public static <T> void requireNoAssociation(MKMVTable<T> table, UUID key, T value) {
        if (table.view().get(key).has(value)) {
            String msg = String.format("%s is associated with %s in this table (%s)",
                    key, value, table.getTableID());
            throw new IllegalArgumentException(msg);
        }
    }
    
    public static <T> void requireAnyAssociation(MKMVTable<T> table, UUID key) {
        if (!table.view().get(key).hasAny()) {
            String msg = String.format("%s is not associated with any value in this table (%s)",
                    key, table.getTableID());
            throw new IllegalArgumentException(msg);
        }
    }
    
    public static <T> void requireNoAssociations(MKMVTable<T> table, UUID key) {
        if (table.view().get(key).hasAny()) {
            String msg = String.format("%s is associated with values in this table (%s)",
                    key, table.getTableID());
            throw new IllegalArgumentException(msg);
        }
    }
}
