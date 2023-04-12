package quality;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

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
        Objects.requireNonNull(secondaryKey);
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
            String onDisk = get(primaryKey, secondaryKey);
            if (onDisk != null) {
                map.put(primaryKey, onDisk);
            }
        }
        return map;
    }
    
    default Map<String, Map<String, String>> getMatching(Predicate<String> pkeyTester,
                                                         Predicate<String> skeyTester,
                                                         Predicate<String> vTester) {
        Objects.requireNonNull(pkeyTester, "PrimaryKey tester predicate cannot be null");
        Objects.requireNonNull(skeyTester, "SecondaryKey tester predicate cannot be null");
        Objects.requireNonNull(vTester, "Value tester predicate cannot be null");

        Map<String, Map<String, String>> map = new HashMap<>();

        for (String primaryKey : primaryKeySet()) {
            if (!pkeyTester.test(primaryKey)) {
                continue;
            }
            for (String secondaryKey : secondaryKeySet(primaryKey)) {
                if (!skeyTester.test(secondaryKey)) {
                    continue;
                }

                String onDisk = get(primaryKey, secondaryKey);
                if (onDisk != null && vTester.test(onDisk)) {
                    map.computeIfAbsent(primaryKey, k -> new HashMap<>())
                       .put(secondaryKey, onDisk);
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
    
    default Set<String> getSecondaryKeysWith(String primaryKey, Predicate<String> vTester) {
        Objects.requireNonNull(vTester, "Value tester predicate cannot be null");
        
        Set<String> set = new HashSet<>();
        for (String secondaryKey : secondaryKeySet(primaryKey)) {
            String onDisk = get(primaryKey, secondaryKey);
            if (onDisk != null && vTester.test(onDisk)) {
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
    
    String remove(String primaryKey, String secondaryKey, Predicate<String> valueTester);
    
    default String remove(String primaryKey, String secondaryKey) {
        return remove(primaryKey, secondaryKey, onDisk -> true);
    }
    
    default Map<String, String> removeAllWithPrimaryKey(String primaryKey) {
        Map<String, String> map = new HashMap<>();
        
        for (String secondaryKey : secondaryKeySet(primaryKey)) {
            String value = remove(primaryKey, secondaryKey);
            map.put(secondaryKey, value);
        }
        return map;
    }
    
    default Map<String, String> removeAllWithSecondaryKey(String secondaryKey) {
        Keys.requireValidKey(secondaryKey);
        Map<String, String> map = new HashMap<>();
        
        for (String primaryKey : primaryKeySet()) {
            String onDisk = remove(primaryKey, secondaryKey);
            if (onDisk != null) {
                map.put(primaryKey, onDisk);
            }
        }
        return map;
    }
    
    default Map<String, Map<String, String>> removeMatching(Predicate<String> pkeyTester,
                                                            Predicate<String> skeyTester,
                                                            Predicate<String> vTester) {
        Objects.requireNonNull(pkeyTester, "PrimaryKey tester predicate cannot be null");
        Objects.requireNonNull(skeyTester, "SecondaryKey tester predicate cannot be null");
        Objects.requireNonNull(vTester, "Value tester predicate cannot be null");
        
        Map<String, Map<String, String>> map = new HashMap<>();
        
        for (String primaryKey : primaryKeySet()) {
            if (!pkeyTester.test(primaryKey)) {
                continue;
            }
            for (String secondaryKey : secondaryKeySet(primaryKey)) {
                if (!skeyTester.test(secondaryKey)) {
                    continue;
                }
                
                String onDisk = remove(primaryKey, secondaryKey, vTester);
                if (onDisk != null) {
                    map.computeIfAbsent(primaryKey, k -> new HashMap<>())
                       .put(secondaryKey, onDisk);
                }
            }
        }
        
        return map;
    }
    
    default Set<String> removeSecondaryKeysWith(String primaryKey, Predicate<String> vTester) {
        Objects.requireNonNull(vTester, "Value tester predicate cannot be null");
        
        Set<String> set = new HashSet<>();
        for (String secondaryKey : secondaryKeySet(primaryKey)) {
            String onDisk = remove(primaryKey, secondaryKey, vTester);
            if (onDisk != null) {
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
                            Predicate<String> ovTester,
                            String newValue) {
        Objects.requireNonNull(ovTester, "OldValue tester predicate cannot be null");
        
        String onDisk = get(primaryKey, secondaryKey);
        if (onDisk != null && ovTester.test(onDisk)) {
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
