package model.metadata;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import model.mappers.Mapper;
import model.mappers.Mappers;
import model.mappers.MutableMapper;
import model.util.SingleIterators;
import model.util.UUIDs;

public final class Metadatas {
    private static abstract class AbstractMetadata implements Metadata {
        private final UUID metadataID = UUID.randomUUID();
        
        public UUID getMetadataID() {
            return metadataID;
        }
        
        public UUID computeTraitID(UUID entityID) {
            return UUIDs.xorUUIDs(metadataID, entityID);
        }
        
        public Trait asTrait(UUID entityID) {
            if (isAssociated(entityID)) {
                return new Trait() {
                    private UUID traitID = computeTraitID(entityID);
                    
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
        
        public UUID mark(UUID entityID) {
            if (set.contains(entityID)) {
                String msg = String.format("%s is already marked with this metadata", entityID);
                throw new IllegalArgumentException(msg);
            } else {
                set.add(entityID);
                return computeTraitID(entityID);
            }
        }
        
        public UUID unmark(UUID entityID) {
            if (!set.contains(entityID)) {
                String msg = String.format("%s is not marked with this metadata", entityID);
                throw new IllegalArgumentException(msg);
            } else {
                set.remove(entityID);
                return computeTraitID(entityID);
            }
        }
    }
    
    private static abstract class AbstractValueMetadata<T> extends AbstractMetadata implements ValueMetadata<T> {
        private final MutableMapper<UUID, T> mapper;
        
        private AbstractValueMetadata(MutableMapper<UUID, T> mapper) {
            this.mapper = mapper;   
        }
        
        public boolean isAssociated(UUID entityID) {
            return mapper.hasKey(entityID);
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
        
        public ValueTrait<T> asValueTrait(UUID entityID) {
            if (isAssociated(entityID)) {
                return new ValueTrait<T>() {
                    private UUID traitID = computeTraitID(entityID);
                    private T value = viewValues().anyValue(entityID);
                    
                    public UUID getTraitID() {
                        return traitID;
                    }
                    
                    public int countValues() {
                        return 1;
                    }
                    
                    public Iterator<T> iterateValues() {
                        return SingleIterators.of(value);
                    }
                    
                    public T anyValue() {
                        return value;
                    }
                    
                    public Set<T> getValues() {
                        return Set.of(value);
                    }
                };
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
        
        public ValueTrait<T> asValueTrait(UUID entityID) {
            if (isAssociated(entityID)) {
                return new ValueTrait<T>() {
                    private UUID traitID = computeTraitID(entityID);
                    private Set<T> values = viewValues().getValues(entityID);
                    
                    public UUID getTraitID() {
                        return traitID;
                    }
                    
                    public int countValues() {
                        return values.size();
                    }
                    
                    public Iterator<T> iterateValues() {
                        return values.iterator();
                    }
                    
                    public T anyValue() {
                        return values.iterator().next();
                    }
                    
                    public Set<T> getValues() {
                        return values;
                    }
                };
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
