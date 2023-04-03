package entity;

import java.util.UUID;

final class SimpleSystemEntity implements SystemEntity {
    private final UUID identity;

    SimpleSystemEntity(UUID identity) {
        this.identity = identity;
    }

    public UUID getIdentity() {
        return identity;
    }

    public String toString() {
        return String.format("SystemEntity<Identity<%s>>", identity);
    }
}
