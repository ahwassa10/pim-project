package entity;

import java.nio.file.Path;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import qualitygardens.selectiontag.SelectionTagGarden;
import statement.FileStatementStore;
import substance.SubstanceStore;

public final class EntitySystem {
    private final FileStatementStore statementStore;
    private final SubstanceStore substanceStore;
    
    private final SelectionTagGarden selectionTagGarden;

    EntitySystem(FileStatementStore statementStore, SubstanceStore substanceStore) {
        this.statementStore = statementStore;
        this.substanceStore = substanceStore;
        
        this.selectionTagGarden = new SelectionTagGarden(this, "selection-tag");
        
        System.out.println("Successfully created an entity system");
    }

    public String createEntity() {
        String entityKey = Keys.createKey();
        statementStore.putDescriptor("identity", entityKey);
        
        String creationTime = Instant.now().toString();
        statementStore.put("creation-time", entityKey, creationTime);
        
        return entityKey;
    }
    
    public Map<String, Map<String, String>> remove(String key) {
        Keys.requiresValidKey(key);
        
        Map<String, Map<String, String>> removed = new HashMap<>();
        
        // Get all statements for which the identifier is part of the qualifier.
        for (String qualifierKey : statementStore.qualifierSet()) {
            if (qualifierKey.contains(key)) {
                Map<String, String> removedQualifications =
                        statementStore.getWithQualifier(qualifierKey);
                removed.put(qualifierKey, removedQualifications);
                continue;
            }
            
            // Get all statements for which the identifier is part of the holder.
            for (String holderKey : statementStore.holderSetFor(qualifierKey)) {
                if (holderKey.contains(key)) {
                    String removedValue = statementStore.get(qualifierKey, holderKey);
                    removed.computeIfAbsent(qualifierKey, k -> new HashMap<>())
                           .put(holderKey, removedValue);
                }
            }
        }
        
        for (String qualifierKey : removed.keySet()) {
            for (String holderKey : removed.get(qualifierKey).keySet()) {
                statementStore.remove(qualifierKey, holderKey);
            }
        }
        
        return removed;
    }
    
    public SelectionTagGarden getSelectionTagGarden() {
        return selectionTagGarden;
    }
    
    public FileStatementStore getStatementStore() {
        return statementStore;
    }

    public void setSubstance(String key, Path substanceFile) {
        Objects.requireNonNull(key, "Key cannot be null");
        
        String hash = substanceStore.capture(substanceFile);
        statementStore.put("substance", key, hash);
    }

    public String toString() {
        return String.format("Entity System<>");
    }
    
    public void printSS() {
        statementStore.printKeyMap();
    }
}
