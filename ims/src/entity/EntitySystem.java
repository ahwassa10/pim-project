package entity;

import java.nio.file.Path;
import java.time.Instant;
import java.util.Objects;

import statement.FileStatementStore;
import substance.SubstanceStore;

public final class EntitySystem {
    private final FileStatementStore qualityStore;
    private final SubstanceStore substanceStore;

    EntitySystem(FileStatementStore qualityStore, SubstanceStore substanceStore) {
        this.qualityStore = qualityStore;
        this.substanceStore = substanceStore;
        
        qualityStore.putDescriptor("keyword", "identity");
        qualityStore.putDescriptor("metadata", "substance");
        qualityStore.putDescriptor("metadata", "creation-time");
        qualityStore.putDescriptor("metadata", "happened-on");
        
        System.out.println("Successfully created an entity system");
    }
    
    public Identifier tag(String keyword, Identifier entity) {
        Objects.requireNonNull(entity, "Entity identifier cannot be null");
        
        String holder = entity.asKey();
        qualityStore.putDescriptor(keyword, holder);
        
        Identifier event = Identifiers.combine(keyword, entity);
        String holder2 = event.asKey();
        String happenedOn = Instant.now().toString();
        qualityStore.put("happened-on", holder2, happenedOn);
        
        return event;
    }

    public Identifier attribute(String type, Identifier entity, String data) {
        Objects.requireNonNull(entity, "Entity identifier cannot be null");
        
        String holder = entity.asKey();
        qualityStore.put(type, holder, data);
        
        Identifier event = Identifiers.combine(type, entity);
        String holder2 = event.asKey();
        String happenedOn = Instant.now().toString();
        qualityStore.put("happened-on", holder2, happenedOn);
        
        return event;
    }

    public Identifier createEntity() {
        Identifier entity = Identifiers.newIdentifier();
        String holder = entity.asKey();
        qualityStore.putDescriptor("identity", holder);
        
        String creationTime = Instant.now().toString();
        qualityStore.put("creation-time", holder, creationTime);
        
        return entity;
    }

    public void setSubstance(Identifier entity, Path substanceFile) {
        Objects.requireNonNull(entity, "Entity identifier cannot be null");
        
        String holder = entity.asKey();
        
        String hash = substanceStore.capture(substanceFile);
        qualityStore.put("substance", holder, hash);
    }

    public String toString() {
        return String.format("Entity System<>");
    }
}
