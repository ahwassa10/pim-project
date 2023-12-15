package model.metadata;

import java.util.UUID;
import java.util.stream.Stream;

import model.mapper.Mapper;

public interface ValueMetadata<T> extends Metadata {
    Mapper<UUID, T> viewValues();
    
    Trait<T> asTrait(UUID entityID);
    
    Stream<Trait<T>> traitStream();
}