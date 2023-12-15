package model.metadata;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import model.mapper.Mapper;
import model.mapper.Mappers;
import model.mapper.MutableMapper;
import model.presence.One;
import model.presence.Some;
import model.util.UUIDs;

public final class Metadatas {
    private static abstract class AbstractMetadata implements Metadata {
        private final UUID metadataID = UUID.randomUUID();
        
        public UUID getMetadataID() {
            return metadataID;
        }
        
        public UUID computeID(UUID entityID) {
            return UUIDs.xorUUIDs(metadataID, entityID);
        }
        
        public Trait asTrait(UUID entityID) {
            if (isAssociated(entityID)) {
                return new Trait() {
                    private UUID traitID = computeID(entityID);
                    
                    public UUID getTraitID() {
                        return traitID;
                    }
                };
            } else {
                String msg = String.format("%s is not associated with this metadata", entityID);
                throw new IllegalArgumentException(msg);
            }
        }
    }
    
    public static class MarkedMetadata extends AbstractMetadata {
        private final Set<UUID> set = new HashSet<>();
        
        public boolean isAssociated(UUID entityID) {
            return set.contains(entityID);
        }
        
        public Stream<UUID> stream() {
            return set.stream().map(entityID -> computeID(entityID));
        }
        
        public UUID mark(UUID entityID) {
            if (set.contains(entityID)) {
                String msg = String.format("%s is already marked with this metadata", entityID);
                throw new IllegalArgumentException(msg);
            } else {
                set.add(entityID);
                return computeID(entityID);
            }
        }
        
        public UUID unmark(UUID entityID) {
            if (!set.contains(entityID)) {
                String msg = String.format("%s is not marked with this metadata", entityID);
                throw new IllegalArgumentException(msg);
            } else {
                set.remove(entityID);
                return computeID(entityID);
            }
        }
    }
    
    private static abstract class AbstractValueMetadata<T> extends AbstractMetadata implements ValueMetadata<T> {
        private final MutableMapper<UUID, T> mapper;
        
        private AbstractValueMetadata(MutableMapper<UUID, T> mapper) {
            this.mapper = mapper;   
        }
        
        public boolean isAssociated(UUID entityID) {
            return mapper.get(entityID).has();
        }
        
        public Stream<UUID> stream() {
            return mapper.keyStream();
        }
        
        public Mapper<UUID, T> viewValues() {
            return mapper;
        }
        
        public void attach(UUID entity, T value) {
            mapper.map(entity, value);
        }
        
        public void detach(UUID entity, T value) {
            mapper.unmap(entity, value);
        }
        
        public void detachAll(UUID entity) {
            mapper.unmapAll(entity);
        }
    }
    
    public static class SingleMetadata<T> extends AbstractValueMetadata<T> {
        private SingleMetadata() {
            super(Mappers.singleMapper());
        }
        
        private ValueTrait<T> buildValueTrait(UUID entityID) {
            return new ValueTrait<T>() {
                private UUID traitID = computeID(entityID);
                private One<T> value = One.of(viewValues().get(entityID).any());
                
                public UUID getTraitID() {
                    return traitID;
                }
                
                public Some<T> get() {
                    return value;
                }
            };
        }
        
        public Stream<ValueTrait<T>> traitStream() {
            return stream().map(entityID -> buildValueTrait(entityID));
        }
        
        public ValueTrait<T> asValueTrait(UUID entityID) {
            if (isAssociated(entityID)) {
                return buildValueTrait(entityID);
            } else {
                String msg = String.format("%s is not associated with this metadata", entityID);
                throw new IllegalArgumentException(msg);
            }
        }
    }
    
    public static class MultiMetadata<T> extends AbstractValueMetadata<T> {
        private MultiMetadata() {
            super(Mappers.multiMapper());
        }
        
        private ValueTrait<T> buildValueTrait(UUID entityID) {
            return new ValueTrait<T>() {
                private UUID traitID = computeID(entityID);
                private Some<T> values = Some.of(viewValues().get(entityID).asSet());
                
                public UUID getTraitID() {
                    return traitID;
                }
                
               public Some<T> get() {
                   return values;
               }
            };
        }
        
        public Stream<ValueTrait<T>> traitStream() {
            return stream().map(entityID -> buildValueTrait(entityID));
        }
        
        public ValueTrait<T> asValueTrait(UUID entityID) {
            if (isAssociated(entityID)) {
                return buildValueTrait(entityID);
            } else {
                String msg = String.format("%s is not associated with this metadata", entityID);
                throw new IllegalArgumentException(msg);
            }
        }
    }
    
    public static MarkedMetadata markedMetadata() {
        return new MarkedMetadata();
    }
    
    public static <T> SingleMetadata<T> singleMetadata() {
        return new SingleMetadata<T>();
    }
    
    public static <T> MultiMetadata<T> multiMetadata() {
        return new MultiMetadata<T>();
    }
}
