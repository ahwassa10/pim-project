package entity;

import java.nio.file.Path;
import java.time.Instant;
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
    
    public Identifier tag(String keyword, Identifier entity) {
        Objects.requireNonNull(entity, "Entity identifier cannot be null");
        
        String holder = entity.asKey();
        statementStore.putDescriptor(keyword, holder);
        
        Identifier event = Identifiers.combine(keyword, entity);
        String holder2 = event.asKey();
        String happenedOn = Instant.now().toString();
        statementStore.put("happened-on", holder2, happenedOn);
        
        return event;
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
