package quality;

import java.util.Objects;
import java.util.Set;

public interface QualityStore {
    void clear();
    
    default boolean containsFullKey(String primaryKey,
                                    String secondaryKey) {
        return primaryKeySet().contains(primaryKey) &&
               secondaryKeySet(primaryKey).contains(secondaryKey);
    }
    
    default boolean containsPrimaryKey(String primaryKey) {
        return primaryKeySet().contains(primaryKey);
    }
    
    default boolean containsSecondaryKey(String secondaryKey) {
        for (String pkey : primaryKeySet()) {
            if (secondaryKeySet(pkey).contains(secondaryKey)) {
                return true;
            }
        }
        return false;
    }
    
    default boolean containsValue(String value) {
        Objects.requireNonNull(value, "Value cannot be null");
        
        for (String pkey : primaryKeySet()) {
            for (String skey : secondaryKeySet(pkey)) {
                String onDisk = get(pkey, skey);
                if (value.equals(onDisk)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    String get(String primaryKey, String secondaryKey);
    
    default String getOrDefault(String primaryKey,
                                String secondaryKey,
                                String defaultValue) {
        String onDisk = get(primaryKey, secondaryKey);
        return onDisk == null ? defaultValue : onDisk;
    }
    
    default boolean isEmpty() {
        return size() == 0;
    }
    
    Set<String> primaryKeySet();
    
    String put(String primarykey, String secondaryKey);
    
    String put(String primaryKey, String secondaryKey, String value);
    
    default String putIfAbsent(String primaryKey,
                               String secondaryKey,
                               String value) {
        String onDisk = get(primaryKey, secondaryKey);
        if (onDisk != null) {
            put(primaryKey, secondaryKey, value);
        }
        return onDisk;
    }
    
    String remove(String primaryKey, String secondaryKey);
    
    default boolean remove(String primaryKey,
                           String secondaryKey, 
                           String value) {
        Objects.requireNonNull(value, "Value cannot be null");
        // This null check is needed, otherwise the .equals()
        // method might blow up below. 
        
        if (containsFullKey(primaryKey, secondaryKey) &&
            value.equals(get(primaryKey, secondaryKey))) {
            
            remove(primaryKey, secondaryKey);
            return true;
        } else {
            return false;
        }
    }
    
    default String replace(String primaryKey,
                           String secondaryKey,
                           String value) {  
        if (containsFullKey(primaryKey, secondaryKey)) {
            return put(primaryKey, secondaryKey, value);
        } else {
            return null;
        }
    }
    
    default boolean replace(String primaryKey,
                            String secondaryKey,
                            String oldValue,
                            String newValue) {
        Objects.requireNonNull(oldValue, "Old value cannot be null");
        
        if (containsFullKey(primaryKey, secondaryKey) &&
            oldValue.equals(get(primaryKey, secondaryKey))) {
            
            put(primaryKey, secondaryKey, newValue);
            return true;
        } else {
            return false;
        }
    }
    
    Set<String> secondaryKeySet(String primaryKey);
    
    default int size() {
        int total = 0;
        for (String pkey : primaryKeySet()) {
            total += secondaryKeySet(pkey).size();
        }
        return total;
    }
}
