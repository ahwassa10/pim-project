package entity;

import java.util.Objects;

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
        Objects.requireNonNull(qualityStore, "Quality Store cannot be null");
        this.qualityStore = qualityStore;
        return this;
    }

    public EntitySystemBuilder setSubstanceStore(SubstanceStore substanceStore) {
        Objects.requireNonNull(substanceStore, "Substance Store cannot be null");
        this.substanceStore = substanceStore;
        return this;
    }
}
