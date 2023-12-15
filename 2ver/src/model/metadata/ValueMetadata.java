package model.metadata;

import java.util.UUID;
import java.util.stream.Stream;

import model.mapper.Mapper;
import model.presence.MaybeSome;

public interface ValueMetadata<T> extends Metadata {
    Mapper<UUID, T> view();
    MaybeSome<T> view(UUID entityID);
    
    Trait<T> asTrait(UUID entityID);
    Stream<Trait<T>> traitStream();
}