package model.metadata;

import java.util.UUID;

import model.presence.Some;

public interface Trait<T> {
    UUID getTraitID();
    Some<T> get();
}