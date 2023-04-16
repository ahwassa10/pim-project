package entity;

import java.nio.file.Path;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import statement.FileStatementStore;
import substance.SubstanceStore;

public final class EntitySystem {
    private final FileStatementStore statementStore;
    private final SubstanceStore substanceStore;

    EntitySystem(FileStatementStore statementStore, SubstanceStore substanceStore) {
        this.statementStore = statementStore;
        this.substanceStore = substanceStore;
        
        statementStore.putDescriptor("keyword", "identity");
        statementStore.putDescriptor("metadata", "substance");
        statementStore.putDescriptor("metadata", "creation-time");
        statementStore.putDescriptor("metadata", "happened-on");
        
        System.out.println("Successfully created an entity system");
    }

    public Identifier attribute(String type, Identifier entity, String data) {
        Objects.requireNonNull(entity, "Entity identifier cannot be null");
        
        String holder = entity.asKey();
        statementStore.put(type, holder, data);
        
        Identifier event = Identifiers.combine(type, entity);
        String holder2 = event.asKey();
        String happenedOn = Instant.now().toString();
        statementStore.put("happened-on", holder2, happenedOn);
        
        return event;
    }

    public Identifier createEntity() {
        Identifier entity = Identifiers.newIdentifier();
        String holder = entity.asKey();
        statementStore.putDescriptor("identity", holder);
        
        String creationTime = Instant.now().toString();
        statementStore.put("creation-time", holder, creationTime);
        
        return entity;
    }
    
    public Map<String, Map<String, String>> remove(Identifier identifier) {
        Objects.requireNonNull(identifier, "Identifier cannot be null");
        
        Map<String, Map<String, String>> map = new HashMap<>();
        String identifierKey = identifier.asKey();
        
        for (String qualifierKey : statementStore.qualifierSet()) {
            if (qualifierKey.contains(identifierKey)) {
                Map<String, String> removedQualifications =
                        statementStore.removeQualifications(qualifierKey);
                map.put(qualifierKey, removedQualifications);
                continue;
            }
            
            for (String holderKey : statementStore.holderSetFor(qualifierKey)) {
                if (holderKey.contains(identifierKey)) {
                    String removedValue = statementStore.remove(qualifierKey, holderKey);
                    map.computeIfAbsent(qualifierKey, k -> new HashMap<>())
                       .put(holderKey, removedValue);
                }
            }
        }
        
        return map;
    }
    
    public FileStatementStore getStatementStore() {
        return statementStore;
    }

    public void setSubstance(Identifier entity, Path substanceFile) {
        Objects.requireNonNull(entity, "Entity identifier cannot be null");
        
        String holder = entity.asKey();
        
        String hash = substanceStore.capture(substanceFile);
        statementStore.put("substance", holder, hash);
    }

    public String toString() {
        return String.format("Entity System<>");
    }
    
    public void printSS() {
        statementStore.printKeyMap();
    }
}
