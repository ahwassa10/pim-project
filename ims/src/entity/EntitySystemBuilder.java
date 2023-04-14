package entity;

import java.util.Objects;

import substance.SubstanceStore;
import substance.SubstanceStoreBuilder;
import triple.FileStatementStore;
import triple.FileStatementStoreBuilder;

public final class EntitySystemBuilder {
    private FileStatementStore qualityStore;
    private SubstanceStore substanceStore;

    public EntitySystemBuilder() {
    }

    public static EntitySystem test_entitysystem() {
        return new EntitySystemBuilder()
                .setQualityStore(FileStatementStoreBuilder.test_triplestore())
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

    public EntitySystemBuilder setQualityStore(FileStatementStore qualityStore) {
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
