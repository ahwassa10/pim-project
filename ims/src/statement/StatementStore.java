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
        Values.requireValidValue(value);
        
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
    
    default Set<String> getHoldersWith(String qualifierKey, Predicate<String> vTester) {
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
    
    default String getOrDefault(String qualifierKey,
                                String holderKey,
                                String defaultValue) {
        Values.requireValidValue(defaultValue);
        String onDisk = get(qualifierKey, holderKey);
        return onDisk == null ? defaultValue : onDisk;
}
    
    default Map<String, String> getQualifications(String qualifierKey) {
        Map<String, String> map = new HashMap<>();
        
        for (String holderKey : holderSetFor(qualifierKey)) {
            String value = get(qualifierKey, holderKey);
            map.put(holderKey, value);
        }
        return map;
    }
    
    default Map<String, String> getQualities(String holderKey) {
        Keys.requireValidKey(holderKey);
        Map<String, String> map = new HashMap<>();
        
        for (String qualifierKey : qualifierSet()) {
            String onDisk = get(qualifierKey, holderKey);
            if (onDisk != null) {
                map.put(qualifierKey, onDisk);
            }
        }
        return map;
    }
    
    default Map<String, Map<String, String>> getStatements(Predicate<String> qkeyTester,
                                                           Predicate<String> hkeyTester,
                                                           Predicate<String> vTester) {
        Objects.requireNonNull(qkeyTester, "Qualifier key tester predicate cannot be null");
        Objects.requireNonNull(hkeyTester, "Holder key tester predicate cannot be null");
        Objects.requireNonNull(vTester, "Value tester predicate cannot be null");

        Map<String, Map<String, String>> map = new HashMap<>();

        for (String qualifierKey : qualifierSet()) {
            if (!qkeyTester.test(qualifierKey)) {
                continue;
            }
            for (String holderKey : holderSetFor(qualifierKey)) {
                if (!hkeyTester.test(holderKey)) {
                    continue;
                }

                String onDisk = get(qualifierKey, holderKey);
                if (onDisk != null && vTester.test(onDisk)) {
                    map.computeIfAbsent(qualifierKey, k -> new HashMap<>())
                       .put(holderKey, onDisk);
                }
            }
        }
        return map;
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
    
    String remove(String qualifierKey, String holderKey, Predicate<String> valueTester);
    
    default String remove(String qualifierKey, String holderKey) {
        return remove(qualifierKey, holderKey, onDisk -> true);
    }
    
    default Map<String, String> removeQualifications(String qualifierKey) {
        Map<String, String> map = new HashMap<>();
        
        for (String holderKey : holderSetFor(qualifierKey)) {
            String onDisk = remove(qualifierKey, holderKey);
            map.put(holderKey, onDisk);
        }
        return map;
    }
    
    default Map<String, String> removeQualities(String holderKey) {
        Keys.requireValidKey(holderKey);
        Map<String, String> map = new HashMap<>();
        
        for (String qualifierKey : qualifierSet()) {
            String onDisk = remove(qualifierKey, holderKey);
            if (onDisk != null) {
                map.put(qualifierKey, onDisk);
            }
        }
        return map;
    }
    
    default Map<String, Map<String, String>> removeStatements(Predicate<String> qkeyTester,
                                                              Predicate<String> hkeyTester,
                                                              Predicate<String> vTester) {
        Objects.requireNonNull(qkeyTester, "Qualifier key tester predicate cannot be null");
        Objects.requireNonNull(hkeyTester, "Holder key tester predicate cannot be null");
        Objects.requireNonNull(vTester, "Value tester predicate cannot be null");
        
        Map<String, Map<String, String>> map = new HashMap<>();
        
        for (String qualifierKey : qualifierSet()) {
            if (!qkeyTester.test(qualifierKey)) {
                continue;
            }
            for (String holderKey : holderSetFor(qualifierKey)) {
                if (!hkeyTester.test(holderKey)) {
                    continue;
                }
                
                String onDisk = remove(qualifierKey, holderKey, vTester);
                if (onDisk != null) {
                    map.computeIfAbsent(qualifierKey, k -> new HashMap<>())
                       .put(holderKey, onDisk);
                }
            }
        }
        
        return map;
    }
    
    default Set<String> removeHoldersWith(String qualifierKey, Predicate<String> vTester) {
        Objects.requireNonNull(vTester, "Value tester predicate cannot be null");
        
        Set<String> set = new HashSet<>();
        for (String holderKey : holderSetFor(qualifierKey)) {
            String onDisk = remove(qualifierKey, holderKey, vTester);
            if (onDisk != null) {
                set.add(holderKey);
            }
        }
        return set;
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
