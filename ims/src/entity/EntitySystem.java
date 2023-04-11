package entity;

import java.nio.file.Path;
import java.util.UUID;

import quality.FileQualityStore;
import substance.SubstanceStore;

public final class EntitySystem {
    private final FileQualityStore qualityStore;
    private final SubstanceStore substanceStore;

    EntitySystem(FileQualityStore qualityStore, SubstanceStore substanceStore) {
        this.qualityStore = qualityStore;
        this.substanceStore = substanceStore;
        
        qualityStore.put("tag", "identity");
        
        System.out.println("Successfully created an entity system");
    }
    
    public void tag(String qualifier, String entity) {
        qualityStore.put(qualifier, entity);
    }

    public void attribute(String qualifier, String entity, String data) {
        qualityStore.put(qualifier, entity, data);
    }

    public String createEntity() {
        String identity = UUID.randomUUID().toString();
        qualityStore.put("identity", identity);
        
        return identity;
    }
    
    public FileQualityStore getQualityStore() {
        return qualityStore;
    }
    
    public SubstanceStore getSubstanceStore() {
        return substanceStore;
    }

    public void setSubstance(String identity, Path substanceFile) {
        String hash = substanceStore.capture(substanceFile);
        qualityStore.put("substance", identity, hash);
    }

    public String toString() {
        return String.format("Entity System<>");
    }
}
