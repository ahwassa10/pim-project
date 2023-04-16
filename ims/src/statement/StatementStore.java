package statement;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

public interface StatementStore {
    void clear();
    
    default boolean containsDescriptor(String qualifierKey,
                                       String holderKey) {
        return qualifierSet().contains(qualifierKey) &&
               holderSetFor(qualifierKey).contains(holderKey);
    }
    
    default boolean containsHolder(String holderKey) {
        for (String qualifierKey : qualifierSet()) {
            if (holderSetFor(qualifierKey).contains(holderKey)) {
                return true;
            }
        }
        return false;
    }
    
    default boolean containsQualifier(String qualifierKey) {
        return qualifierSet().contains(qualifierKey);
    }
    
    default boolean containsValue(String value) {
        Objects.requireNonNull(value, "Value cannot be null");
        
        for (String qualifierKey : qualifierSet()) {
            for (String holderKey : holderSetFor(qualifierKey)) {
                if (value.equals(get(qualifierKey, holderKey))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    Set<String> holderSetFor(String qualifierKey);
    
    String get(String qualifierKey, String holderKey);
    
    default String getOrDefault(String qualifierKey,
                                String holderKey,
                                String defaultValue) {
        Objects.requireNonNull(defaultValue, "Default value cannot be null");
        String onDisk = get(qualifierKey, holderKey);
        return onDisk == null ? defaultValue : onDisk;
    }
    
    default Map<String, String> getWithHolder(String holderKey) {
        Map<String, String> map = new HashMap<>();
        
        for (String qualifierKey : qualifierSet()) {
            String onDisk = get(qualifierKey, holderKey);
            if (onDisk != null) {
                map.put(qualifierKey, onDisk);
            }
        }
        return map;
    }
    
    default Map<String, String> getWithQualifier(String qualifierKey) {
        Map<String, String> map = new HashMap<>();
        
        for (String holderKey : holderSetFor(qualifierKey)) {
            String value = get(qualifierKey, holderKey);
            map.put(holderKey, value);
        }
        return map;
    }
    
    default Set<String> getWithQuality(String qualifierKey, Predicate<String> vTester) {
        Objects.requireNonNull(vTester, "Value tester predicate cannot be null");
        
        Set<String> set = new HashSet<>();
        for (String holderKey : holderSetFor(qualifierKey)) {
            String onDisk = get(qualifierKey, holderKey);
            if (onDisk != null && vTester.test(onDisk)) {
                set.add(holderKey);
            }
        }
        return set;
    }
    
    default boolean isEmpty() {
        return size() == 0;
    }
    
    String put(String qualifierKey, String holderKey, String value);
    
    String putDescriptor(String qualifierKey, String holderKey);
    
    default String putIfAbsent(String qualifierKey,
                               String holderKey,
                               String value) {
        String onDisk = get(qualifierKey, holderKey);
        if (onDisk == null) {
            put(qualifierKey, holderKey, value);
        }
        return onDisk;
    }
    
    Set<String> qualifierSet();
    
    void remove(String qualifierKey, String holderKey);
    
    default String removeWithDescriptor(String qualifierKey, String holderKey) {
        String removed = get(qualifierKey, holderKey);
        
        if (removed != null) {
            remove(qualifierKey, holderKey);
        }
        
        return removed;
    }
    
    default Map<String, String> removeWithHolder(String holderKey) {
        Map<String, String> removed = getWithHolder(holderKey);
        
        for (String qualifierKey : removed.keySet()) {
            remove(qualifierKey, holderKey);
        }
        
        return removed;
    }
    
    default Map<String, String> removeWithQualifier(String qualifierKey) {
        Map<String, String> removed = getWithQualifier(qualifierKey);
        
        for (String holderKey : removed.keySet()) {
            remove(qualifierKey, holderKey);
        }
        
        return removed;
    }
    
    
    default Set<String> removeWithQuality(String qualifierKey, Predicate<String> vTester) {
        Objects.requireNonNull(vTester, "Value tester predicate cannot be null");
        
        Set<String> removed = getWithQuality(qualifierKey, vTester);
        for (String holderKey : removed) {
            remove(qualifierKey, holderKey);
        }
        
        return removed;
    }
    
    default String replace(String qualifierKey,
                           String holderKey,
                           String value) {  
        if (containsDescriptor(qualifierKey, holderKey)) {
            return put(qualifierKey, holderKey, value);
        } else {
            return null;
        }
    }
    
    default boolean replace(String qualifierKey,
                            String holderKey,
                            Predicate<String> ovTester,
                            String newValue) {
        Objects.requireNonNull(ovTester, "OldValue tester predicate cannot be null");
        
        String onDisk = get(qualifierKey, holderKey);
        if (onDisk != null && ovTester.test(onDisk)) {
            put(qualifierKey, holderKey, newValue);
            return true;
        } else {
            return false;
        }
    }
    
    default int size() {
        int total = 0;
        for (String qualifierKey : qualifierSet()) {
            total += holderSetFor(qualifierKey).size();
        }
        return total;
    }
}
