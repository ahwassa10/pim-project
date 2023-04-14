package entity;

import java.nio.file.Path;
import java.util.UUID;

import substance.SubstanceStore;
import triple.FileStatementStore;

public final class EntitySystem {
    private final FileStatementStore qualityStore;
    private final SubstanceStore substanceStore;

    EntitySystem(FileStatementStore qualityStore, SubstanceStore substanceStore) {
        this.qualityStore = qualityStore;
        this.substanceStore = substanceStore;
        
        qualityStore.putDescriptor("capability", "tag");
        qualityStore.putDescriptor("capability", "attribute");
        
        qualityStore.putDescriptor("tag", "identity");
        qualityStore.putDescriptor("attribute", "substance");
        qualityStore.putDescriptor("attribute", "creation-time");
        
        System.out.println("Successfully created an entity system");
    }
    
    public void tag(String qualifier, String entity) {
        qualityStore.putDescriptor(qualifier, entity);
    }

    public void attribute(String qualifier, String entity, String data) {
        qualityStore.put(qualifier, entity, data);
    }

    public String createEntity() {
        String identity = UUID.randomUUID().toString();
        qualityStore.putDescriptor("identity", identity);
        
        return identity;
    }

    public void setSubstance(String identity, Path substanceFile) {
        String hash = substanceStore.capture(substanceFile);
        qualityStore.put("substance", identity, hash);
    }

    public String toString() {
        return String.format("Entity System<>");
    }
}
