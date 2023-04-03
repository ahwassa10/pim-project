package entity;

import quality.QualityStore;
import quality.QualityStoreBuilder;
import substance.SubstanceStore;
import substance.SubstanceStoreBuilder;

public final class EntitySystemBuilder {
    private QualityStore qualityStore;
    private SubstanceStore substanceStore;

    public EntitySystemBuilder() {
    }

    public static EntitySystem test_entitysystem() {
        return new EntitySystemBuilder()
                .setQualityStore(QualityStoreBuilder.test_qualitystore())
                .setSubstanceStore(SubstanceStoreBuilder.test_substore())
                .build();
    }

    public EntitySystem build() {
        if (qualityStore == null) {
            throw new IllegalStateException("Quality Store not specified");
        } else if (substanceStore == null) {
            throw new IllegalStateException("Substance Store not specified");
        }
        return new EntitySystem(qualityStore, substanceStore);
    }

    public EntitySystemBuilder setQualityStore(QualityStore qualityStore) {
        if (qualityStore == null) {
            throw new IllegalArgumentException("Quality Store cannot be null");
        }
        this.qualityStore = qualityStore;
        return this;
    }

    public EntitySystemBuilder setSubstanceStore(SubstanceStore substanceStore) {
        if (substanceStore == null) {
            throw new IllegalArgumentException("Substance Store cannot be null");
        }
        this.substanceStore = substanceStore;
        return this;
    }
}
