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
        
        qualityStore.putKeys("capability", "tag");
        qualityStore.putKeys("capability", "attribute");
        
        qualityStore.putKeys("tag", "identity");
        qualityStore.putKeys("attribute", "substance");
        qualityStore.putKeys("attribute", "creation-time");
        
        System.out.println("Successfully created an entity system");
    }
    
    public void tag(String qualifier, String entity) {
        qualityStore.putKeys(qualifier, entity);
    }

    public void attribute(String qualifier, String entity, String data) {
        qualityStore.put(qualifier, entity, data);
    }

    public String createEntity() {
        String identity = UUID.randomUUID().toString();
        qualityStore.putKeys("identity", identity);
        
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
