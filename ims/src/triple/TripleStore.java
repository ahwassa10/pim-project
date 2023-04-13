package triple;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

public interface TripleStore {
    void clear();
    
    default boolean containsDescriptor(String qualifierKey,
                                       String entityKey) {
        return qualifierSet().contains(qualifierKey) &&
               entitySetFor(qualifierKey).contains(entityKey);
    }
    
    default boolean containsEntity(String entityKey) {
        for (String pkey : qualifierSet()) {
            if (entitySetFor(pkey).contains(entityKey)) {
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
        
        for (String qkey : qualifierSet()) {
            for (String ekey : entitySetFor(qkey)) {
                if (value.equals(get(qkey, ekey))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    Set<String> entitySetFor(String qualifierKey);
    
    String get(String qualifierKey, String entityKey);
    
    default Set<String> getEntitiesWith(String qualifierKey, Predicate<String> vTester) {
        Objects.requireNonNull(vTester, "Value tester predicate cannot be null");
        
        Set<String> set = new HashSet<>();
        for (String entityKey : entitySetFor(qualifierKey)) {
            String onDisk = get(qualifierKey, entityKey);
            if (onDisk != null && vTester.test(onDisk)) {
                set.add(entityKey);
            }
        }
        return set;
    }
    
    default String getOrDefault(String qualifierKey,
                                String entityKey,
                                String defaultValue) {
        Values.requireValidValue(defaultValue);
        String onDisk = get(qualifierKey, entityKey);
        return onDisk == null ? defaultValue : onDisk;
}
    
    default Map<String, String> getQualifications(String qualifierKey) {
        Map<String, String> map = new HashMap<>();
        
        for (String entityKey : entitySetFor(qualifierKey)) {
            String value = get(qualifierKey, entityKey);
            map.put(entityKey, value);
        }
        return map;
    }
    
    default Map<String, String> getQualities(String entityKey) {
        Keys.requireValidKey(entityKey);
        Map<String, String> map = new HashMap<>();
        
        for (String qualifierKey : qualifierSet()) {
            String onDisk = get(qualifierKey, entityKey);
            if (onDisk != null) {
                map.put(qualifierKey, onDisk);
            }
        }
        return map;
    }
    
    default Map<String, Map<String, String>> getTriples(Predicate<String> qkeyTester,
                                                        Predicate<String> ekeyTester,
                                                        Predicate<String> vTester) {
        Objects.requireNonNull(qkeyTester, "Qualifier key tester predicate cannot be null");
        Objects.requireNonNull(ekeyTester, "Entity key tester predicate cannot be null");
        Objects.requireNonNull(vTester, "Value tester predicate cannot be null");

        Map<String, Map<String, String>> map = new HashMap<>();

        for (String qualifierKey : qualifierSet()) {
            if (!qkeyTester.test(qualifierKey)) {
                continue;
            }
            for (String entityKey : entitySetFor(qualifierKey)) {
                if (!ekeyTester.test(entityKey)) {
                    continue;
                }

                String onDisk = get(qualifierKey, entityKey);
                if (onDisk != null && vTester.test(onDisk)) {
                    map.computeIfAbsent(qualifierKey, k -> new HashMap<>())
                       .put(entityKey, onDisk);
                }
            }
        }
        return map;
    }
    
    default boolean isEmpty() {
        return size() == 0;
    }
    
    String put(String qualifierKey, String entityKey, String value);
    
    String putDescriptor(String qualifierKey, String entityKey);
    
    default String putIfAbsent(String qualifierKey,
                               String entityKey,
                               String value) {
        String onDisk = get(qualifierKey, entityKey);
        if (onDisk == null) {
            put(qualifierKey, entityKey, value);
        }
        return onDisk;
    }
    
    Set<String> qualifierSet();
    
    String remove(String qualifierKey, String entityKey, Predicate<String> valueTester);
    
    default String remove(String qualifierKey, String entityKey) {
        return remove(qualifierKey, entityKey, onDisk -> true);
    }
    
    default Map<String, String> removeQualifications(String qualifierKey) {
        Map<String, String> map = new HashMap<>();
        
        for (String entityKey : entitySetFor(qualifierKey)) {
            String onDisk = remove(qualifierKey, entityKey);
            map.put(entityKey, onDisk);
        }
        return map;
    }
    
    default Map<String, String> removeQualities(String entityKey) {
        Keys.requireValidKey(entityKey);
        Map<String, String> map = new HashMap<>();
        
        for (String qualifierKey : qualifierSet()) {
            String onDisk = remove(qualifierKey, entityKey);
            if (onDisk != null) {
                map.put(qualifierKey, onDisk);
            }
        }
        return map;
    }
    
    default Map<String, Map<String, String>> removeTriples(Predicate<String> qkeyTester,
                                                           Predicate<String> ekeyTester,
                                                           Predicate<String> vTester) {
        Objects.requireNonNull(qkeyTester, "Qualifier key tester predicate cannot be null");
        Objects.requireNonNull(ekeyTester, "Entity key tester predicate cannot be null");
        Objects.requireNonNull(vTester, "Value tester predicate cannot be null");
        
        Map<String, Map<String, String>> map = new HashMap<>();
        
        for (String qualifierKey : qualifierSet()) {
            if (!qkeyTester.test(qualifierKey)) {
                continue;
            }
            for (String entityKey : entitySetFor(qualifierKey)) {
                if (!ekeyTester.test(entityKey)) {
                    continue;
                }
                
                String onDisk = remove(qualifierKey, entityKey, vTester);
                if (onDisk != null) {
                    map.computeIfAbsent(qualifierKey, k -> new HashMap<>())
                       .put(entityKey, onDisk);
                }
            }
        }
        
        return map;
    }
    
    default Set<String> removeEntitiesWith(String qualifierKey, Predicate<String> vTester) {
        Objects.requireNonNull(vTester, "Value tester predicate cannot be null");
        
        Set<String> set = new HashSet<>();
        for (String entityKey : entitySetFor(qualifierKey)) {
            String onDisk = remove(qualifierKey, entityKey, vTester);
            if (onDisk != null) {
                set.add(entityKey);
            }
        }
        return set;
    }
    
    default String replace(String qualifierKey,
                           String entityKey,
                           String value) {  
        if (containsDescriptor(qualifierKey, entityKey)) {
            return put(qualifierKey, entityKey, value);
        } else {
            return null;
        }
    }
    
    default boolean replace(String qualifierKey,
                            String entityKey,
                            Predicate<String> ovTester,
                            String newValue) {
        Objects.requireNonNull(ovTester, "OldValue tester predicate cannot be null");
        
        String onDisk = get(qualifierKey, entityKey);
        if (onDisk != null && ovTester.test(onDisk)) {
            put(qualifierKey, entityKey, newValue);
            return true;
        } else {
            return false;
        }
    }
    
    default int size() {
        int total = 0;
        for (String qualifierKey : qualifierSet()) {
            total += entitySetFor(qualifierKey).size();
        }
        return total;
    }
}
