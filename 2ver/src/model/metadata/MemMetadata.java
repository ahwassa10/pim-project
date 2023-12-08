package model.metadata;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import model.mappers.Mapper;
import model.mappers.Mappers;
import model.mappers.MutableMapper;
import model.util.SingleIterators;
import model.util.UUIDs;

public final class MemMetadata {
    private static abstract class AbstractMetadata<T> implements ValueMetadata<T> {
        private final UUID metadataID;
        
        private final MutableMapper<UUID, T> mapper;
        
        private AbstractMetadata(UUID uuid, MutableMapper<UUID, T> mapper) {
            this.metadataID = uuid;
            this.mapper = mapper;
        }
        
        public UUID getMetadataID() {
            return metadataID;
        }
        
        public Mapper<UUID, Boolean> viewAssociations() {
            return new Mapper<UUID, Boolean>() {
                public boolean hasMapping(UUID entityID, Boolean value) {
                    if (mapper.hasValues(entityID) && value) {
                        return true;
                    } else if (!mapper.hasValues(entityID) && !value) {
                        return true;
                    } else {
                        return false;
                    }
                }
                
                public int countValues(UUID entityID) {
                    return 1;
                }
                
                public Iterator<Boolean> iterateValues(UUID entityID) {
                    return SingleIterators.of(mapper.hasValues(entityID));
                }
                
                public boolean hasValues(UUID entityID) {
                    return true;
                }
                
                public Boolean anyValue(UUID entityID) {
                    return mapper.hasValues(entityID);
                }
                
                public Set<Boolean> getValues(UUID entityID) {
                    return Set.of(mapper.hasValues(entityID));
                }
            };
        }
        
        public Mapper<UUID, UUID> viewTraits() {
            return new Mapper<UUID, UUID>() {
                public boolean hasMapping(UUID entityID, UUID traitID) {
                    return mapper.hasValues(entityID) && Objects.equals(traitID, UUIDs.xorUUIDs(entityID, metadataID));
                }
                
                public int countValues(UUID entityID) {
                    return Math.min(1, mapper.countValues(entityID));
                }
                
                public Iterator<UUID> iterateValues(UUID entityID) {
                    if (mapper.hasValues(entityID)) {
                        return SingleIterators.of(UUIDs.xorUUIDs(entityID, metadataID));
                    } else {
                        return Collections.emptyIterator();
                    }
                }
                
                public boolean hasValues(UUID entityID) {
                    return mapper.hasValues(entityID);
                }
                
                public UUID anyValue(UUID entityID) {
                    Mappers.requireValues(mapper, entityID);
                    
                    return UUIDs.xorUUIDs(entityID, metadataID);
                }
                
                public Set<UUID> getValues(UUID entityID) {
                    Mappers.requireValues(mapper, entityID);
                    
                    return Set.of(UUIDs.xorUUIDs(entityID, metadataID));
                }
            };
        }
        
        public Trait asTrait(UUID entity) {
            Mappers.requireValues(mapper, entity);
            
            return new Trait() {
                public UUID getTraitID() {
                    return UUIDs.xorUUIDs(entity, metadataID);
                }
            };
        }
        
        public Mapper<UUID, T> viewValues() {
            return mapper;
        }
        
        public ValueTrait<T> asValueTrait(UUID entityID) {
            return new ValueTrait<T>() {
                public UUID getTraitID() {
                    return UUIDs.xorUUIDs(entityID, metadataID);
                }
                
                public int countValues() {
                    return mapper.countValues(entityID);
                }
                
                public Iterator<T> iterateValues() {
                    return mapper.iterateValues(entityID);
                }
                
                public T anyValue() {
                    return mapper.anyValue(entityID);
                }
                
                public Set<T> getValues() {
                    return mapper.getValues(entityID);
                }
            };
        }
        
        public UUID attach(UUID entity, T value) {
            mapper.map(entity, value);
            
            return UUIDs.xorUUIDs(metadataID, entity);
        }
        
        public void detach(UUID entity, T value) {
            mapper.unmap(entity, value);
        }
        
        public void detachAll(UUID entity) {
            mapper.unmapAll(entity);
        }
    }
    
    public static class SingleMetadata<T> extends AbstractMetadata<T> {
        private SingleMetadata() {
            super(UUID.randomUUID(), Mappers.singleMapper());
        }
    }
    
    public static class MultiMetadata<T> extends AbstractMetadata<T> {
        private MultiMetadata() {
            super(UUID.randomUUID(), Mappers.multiMapper());
        }
    }
    
    public static class MarkingMetadata implements MarkedMetadata {
        private final UUID metadataID = UUID.randomUUID();
        
        private Set<UUID> entities = new HashSet<>();
        
        public UUID getMetadataID() {
            return metadataID;
        }
        
        public Mapper<UUID, Boolean> viewAssociations() {
            return new Mapper<UUID, Boolean>() {
                public boolean hasMapping(UUID entityID, Boolean value) {
                    if (entities.contains(entityID) && value) {
                        return true;
                    } else if (!entities.contains(entityID) && !value) {
                        return true;
                    } else {
                        return false;
                    }
                }
                
                public int countValues(UUID entityID) {
                    return 1;
                }
                
                public Iterator<Boolean> iterateValues(UUID entityID) {
                    return SingleIterators.of(entities.contains(entityID));
                }
                
                public boolean hasValues(UUID entityID) {
                    return true;
                }
                
                public Boolean anyValue(UUID entityID) {
                    return entities.contains(entityID);
                }
                
                public Set<Boolean> getValues(UUID entityID) {
                    return Set.of(entities.contains(entityID));
                }
            };
        }
        
        public Mapper<UUID, UUID> viewTraits() {
            return new Mapper<UUID, UUID>() {
                public boolean hasMapping(UUID entityID, UUID traitID) {
                    return entities.contains(entityID) && Objects.equals(traitID, UUIDs.xorUUIDs(entityID, metadataID));
                }
                
                public int countValues(UUID entityID) {
                    return entities.contains(entityID) ? 1 : 0;
                }
                
                public Iterator<UUID> iterateValues(UUID entityID) {
                    if (entities.contains(entityID)) {
                        return SingleIterators.of(UUIDs.xorUUIDs(entityID, metadataID));
                    } else {
                        return Collections.emptyIterator();
                    }
                }
                
                public boolean hasValues(UUID entityID) {
                    return entities.contains(entityID);
                }
                
                public UUID anyValue(UUID entityID) {
                    if (!entities.contains(entityID)) {
                        throw new IllegalArgumentException("No association");
                    }
                    
                    return UUIDs.xorUUIDs(entityID, metadataID);
                }
                
                public Set<UUID> getValues(UUID entityID) {
                    if (!entities.contains(entityID)) {
                        throw new IllegalArgumentException("No association");
                    }
                    
                    return Set.of(UUIDs.xorUUIDs(entityID, metadataID));
                }
            };
        }
        
        public Trait asTrait(UUID entityID) {
            return new Trait() {
                public UUID getTraitID() {
                    return UUIDs.xorUUIDs(entityID, metadataID);
                }
            };
        }
        
        public UUID mark(UUID entityID) {
            entities.add(entityID);
            return UUIDs.xorUUIDs(entityID, metadataID);
        }
        
        public UUID unmark(UUID entityID) {
            entities.remove(entityID);
            return UUIDs.xorUUIDs(entityID, metadataID);
        }
    }
    
    public static <T> SingleMetadata<T> singleMetadata() {
        return new SingleMetadata<T>();
    }
    
    public static <T> MultiMetadata<T> multiMetadata() {
        return new MultiMetadata<T>();
    }
    
    public static MarkingMetadata markingMetadata() {
        return new MarkingMetadata();
    }
}