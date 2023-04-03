package entity;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import quality.QualityStore;
import substance.SubstanceStore;

public final class EntitySystem {
    private final QualityStore qualityStore;
    private final SubstanceStore substanceStore;

    EntitySystem(QualityStore qualityStore, SubstanceStore substanceStore) {
        this.qualityStore = qualityStore;
        this.substanceStore = substanceStore;
        System.out.println("Successfully created an entity system");
    }

    public void attribute(String agent, String quality, String entity, String value) {
        try {
            qualityStore.store(agent, quality, entity, value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String createEntity() {
        String identity = UUID.randomUUID().toString();
        try {
            qualityStore.store("System", "Identity", identity, identity);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return identity;
    }
    
    public QualityStore getQualityStore() {
        return qualityStore;
    }

    public void setSubstance(String identity, Path substanceFile) {
        try {
            String hash = substanceStore.capture(substanceFile);
            qualityStore.store("System", "Substance", identity, hash);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return String.format("Entity System<>");
    }
}
