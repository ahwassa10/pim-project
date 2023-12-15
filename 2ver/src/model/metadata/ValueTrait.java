package model.metadata;

import model.presence.Some;

public interface ValueTrait<T> extends Trait {
    Some<T> get();
}