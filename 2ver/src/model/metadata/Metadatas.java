package model.metadata;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import model.mapper.Mapper;
import model.mapper.Mappers;
import model.mapper.Mappers.MultiMapper;
import model.mapper.Mappers.SingleMapper;
import model.presence.Maybe;
import model.presence.MaybeSome;
import model.presence.Some;

public final class Metadatas {
    public static class Association implements Metadata {
        private final UUID metadataID = UUID.randomUUID();
        private final Set<UUID> set = new HashSet<>();
        
        public UUID getID() {
            return metadataID;
        }
        
        public boolean contains(UUID entityID) {
            return set.contains(entityID);
        }
        
        public Stream<UUID> stream() {
            return set.stream().map(entityID -> computeID(entityID));
        }
        
        public UUID associate(UUID entityID) {
            if (set.contains(entityID)) {
                String msg = String.format("%s is already associated with this (%s) association",
                        entityID, getID());
                throw new IllegalArgumentException(msg);
            } else {
                set.add(entityID);
                return computeID(entityID);
            }
        }
        
        public UUID unmark(UUID entityID) {
            if (!set.contains(entityID)) {
                String msg = String.format("%s is not associated with this (%s) association",
                        entityID, getID());
                throw new IllegalArgumentException(msg);
            } else {
                set.remove(entityID);
                return computeID(entityID);
            }
        }
    }
    
    private static abstract class AbstractTable<T> implements ValueMetadata<T> {
        private final UUID metadataID = UUID.randomUUID();
        
        public UUID getID() {
            return metadataID;
        }
        
        public boolean contains(UUID entityID) {
            return view().get(entityID).has();
        }
        
        public Stream<UUID> stream() {
            return view().keyStream();
        }
        
        private Trait<T> buildValueTrait(UUID entityID) {
            return new Trait<T>() {
                private UUID traitID = computeID(entityID);
                private Some<T> value = view().get(entityID).certainly();
                
                public UUID getTraitID() {
                    return traitID;
                }
                
                public Some<T> get() {
                    return value;
                }
            };
        }
        
        public Trait<T> asTrait(UUID entityID) {
            if (contains(entityID)) {
                return buildValueTrait(entityID);
            } else {
                String msg = String.format("%s is not contains in this (%s) table",
                        entityID, getID());
                throw new IllegalArgumentException(msg);
            }
        }
        
        public Stream<Trait<T>> traitStream() {
            return stream().map(entityID -> buildValueTrait(entityID));
        }
    }
    
    public static class SingleTable<T> extends AbstractTable<T> {
        private final SingleMapper<UUID, T> mapper = Mappers.singleMapper();
        
        private SingleTable() {}
        
        public Mapper<UUID, T> view() {
            return mapper;
        }
        
        public Maybe<T> view(UUID entityID) {
            return mapper.get(entityID);
        }
        
        public UUID attach(UUID entity, T value) {
            mapper.map(entity, value);
            return computeID(entity);
        }
        
        public void detach(UUID entity, T value) {
            mapper.unmap(entity, value);
        }
        
        public void detachAll(UUID entity) {
            mapper.unmapAll(entity);
        }
    }
    
    public static class MultiTable<T> extends AbstractTable<T> {
        private final MultiMapper<UUID, T> mapper = Mappers.multiMapper();
        
        private MultiTable() {}
        
        public Mapper<UUID, T> view() {
            return mapper;
        }
        
        public MaybeSome<T> view(UUID entityID) {
            return mapper.get(entityID);
        }
        
        public UUID attach(UUID entity, T value) {
            mapper.map(entity, value);
            return computeID(entity);
        }
        
        public void detach(UUID entity, T value) {
            mapper.unmap(entity, value);
        }
        
        public void detachAll(UUID entity) {
            mapper.unmapAll(entity);
        }
    }
    
    public static Association markedMetadata() {
        return new Association();
    }
    
    public static <T> SingleTable<T> singleMetadata() {
        return new SingleTable<T>();
    }
    
    public static <T> MultiTable<T> multiMetadata() {
        return new MultiTable<T>();
    }
}
