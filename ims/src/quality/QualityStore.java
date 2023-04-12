package quality;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
        Values.requireValidValue(value);
        
        for (String pkey : primaryKeySet()) {
            for (String skey : secondaryKeySet(pkey)) {
                if (value.equals(get(pkey, skey))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    String get(String primaryKey, String secondaryKey);
    
    default Map<String, String> getAllWithPrimaryKey(String primaryKey) {
        Keys.requireValidKey(primaryKey);
        Map<String, String> map = new HashMap<>();
        
        for (String secondaryKey : secondaryKeySet(primaryKey)) {
            String value = get(primaryKey, secondaryKey);
            map.put(secondaryKey, value);
        }
        return map;
    }
    
    default Map<String, String> getAllWithSecondaryKey(String secondaryKey) {
        Keys.requireValidKey(secondaryKey);
        Map<String, String> map = new HashMap<>();
        
        for (String primaryKey : primaryKeySet()) {
            if (containsFullKey(primaryKey, secondaryKey)) {
                String value = get(primaryKey, secondaryKey);
                map.put(primaryKey, value);
            }
        }
        return map;
    }
    
    default Map<String, Set<String>> getAllWithValue(String value) {
        Values.requireValidValue(value);
        Map<String, Set<String>> map = new HashMap<>();
        
        for (String primaryKey : primaryKeySet()) {
            for (String secondaryKey : secondaryKeySet(primaryKey)) {
                String onDisk = get(primaryKey, secondaryKey);
                
                if (value.equals(onDisk)) {
                    map.computeIfAbsent(primaryKey, k -> new HashSet<>())
                       .add(secondaryKey);
                }
            }
        }
        return map;
    }
    
    default String getOrDefault(String primaryKey,
                                String secondaryKey,
                                String defaultValue) {
        Values.requireValidValue(defaultValue);
        String onDisk = get(primaryKey, secondaryKey);
        return onDisk == null ? defaultValue : onDisk;
    }
    
    default Set<String> getSecondaryKeysWith(String primaryKey, String value) {
        Values.requireValidValue(value);
        
        Set<String> set = new HashSet<>();
        for (String secondaryKey : secondaryKeySet(primaryKey)) {
            if (containsFullKey(primaryKey, secondaryKey) &&
                value.equals(get(primaryKey, secondaryKey))) {
                
                set.add(secondaryKey);
            }
        }
        return set;
    }
    
    default boolean isEmpty() {
        return size() == 0;
    }
    
    Set<String> primaryKeySet();
    
    String put(String primaryKey, String secondaryKey, String value);
    
    String putKeys(String primaryKey, String secondaryKey);
    
    default String putIfAbsent(String primaryKey,
                               String secondaryKey,
                               String value) {
        String onDisk = get(primaryKey, secondaryKey);
        if (onDisk == null) {
            put(primaryKey, secondaryKey, value);
        }
        return onDisk;
    }
    
    String remove(String primaryKey, String secondaryKey);
    
    boolean remove(String primaryKey, String secondaryKey, String value);
    
    default Map<String, String> removeAllWithPrimaryKey(String primaryKey) {
        Map<String, String> map = new HashMap<>();
        
        for (String secondaryKey : secondaryKeySet(primaryKey)) {
            String value = remove(primaryKey, secondaryKey);
            map.put(secondaryKey, value);
        }
        return map;
    }
    
    default Map<String, String> removeAllWithSecondaryKey(String secondaryKey) {
        Map<String, String> map = new HashMap<>();
        
        for (String primaryKey : primaryKeySet()) {
            if (containsFullKey(primaryKey, secondaryKey)) {
                String value = remove(primaryKey, secondaryKey);
                map.put(primaryKey, value);
            }
        }
        return map;
    }
    
    default Map<String, Set<String>> removeAllWithValue(String value) {
        Values.requireValidValue(value);
        Map<String, Set<String>> map = new HashMap<>();
        
        for (String primaryKey : primaryKeySet()) {
            for (String secondaryKey : secondaryKeySet(primaryKey)) {
                if (remove(primaryKey, secondaryKey, value)) {
                    map.computeIfAbsent(primaryKey, k -> new HashSet<>())
                       .add(secondaryKey);
                }
            }
        }
        return map;
    }
    
    default Set<String> removeSecondaryKeysWith(String primaryKey, String value) {
        Values.requireValidValue(value);
        
        Set<String> set = new HashSet<>();
        for (String secondaryKey : secondaryKeySet(primaryKey)) {
            if (remove(primaryKey, secondaryKey, value)) {
                set.add(secondaryKey);
            }
        }
        return set;
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
        Values.requireValidValue(oldValue);
        
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
