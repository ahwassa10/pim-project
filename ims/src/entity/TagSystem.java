package entity;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import statement.StatementStore;


public final class TagSystem {
    private final EntitySystem entitySystem;
    private final StatementStore statementStore;
    private final Set<String> tagNameSet = new HashSet<>();
    private final String tagSystemNameKey;
    
    TagSystem(EntitySystem entitySystem, String tagSystemNameKey) {
        this.entitySystem = entitySystem;
        this.statementStore = entitySystem.getStatementStore();
        this.tagSystemNameKey = tagSystemNameKey;
    }
    
    public Identifier associate(TagIdentifier tag, Identifier identifier) {
        Objects.requireNonNull(identifier, "Identifier cannot be null");
        
        if (!owns(tag)) {
            String msg = String.format("%s is not from the TagSystem: %s",
                    tag.asKey(), tagSystemNameKey);
            throw new IllegalArgumentException(msg);
        }
        
        String qualifierKey = tag.asKey();
        String holderKey = identifier.asKey();
        statementStore.putDescriptor(qualifierKey, holderKey);
        
        return Identifiers.combine(tag, identifier);
    }
    
    public boolean contains(String tagNameKey) {
        return tagNameSet.contains(tagNameKey);
    }
    
    public TagIdentifier createAndAdd(String tagNameKey) {
        TagIdentifiers.requireValidTagName(tagNameKey);
        
        if (!tagNameSet.contains(tagNameKey)) {
            tagNameSet.add(tagNameKey);
            statementStore.putDescriptor(tagSystemNameKey, tagNameKey);
        }
        
        return new SimpleTagIdentifier(tagSystemNameKey, tagNameKey);
    }
    
    public Map<String, Map<String, String>> dissociate(TagIdentifier tag, Identifier identifier) {
        Objects.requireNonNull(identifier, "Identifier cannot be null");
        
        if (!owns(tag)) {
            String msg = String.format("%s is not from the TagSystem: %s",
                    tag.asKey(), tagSystemNameKey);
            throw new IllegalArgumentException(msg);
        }
        
        String qualifierKey = tag.asKey();
        String holderKey = identifier.asKey();
        
        if (statementStore.containsDescriptor(qualifierKey, holderKey)) { 
            // Remove the creation-time and other associations of the 
            // association.
            Identifier association = Identifiers.combine(tag, identifier);
            Map<String, Map<String, String>> removed = entitySystem.remove(association);
            
            // Remove the association itself.
            String value = statementStore.removeWithDescriptor(qualifierKey, holderKey);
            removed.computeIfAbsent(qualifierKey, k -> new HashMap<>())
                   .put(holderKey, value);
            
            return removed;
        }
        
        return null;
    }
    
    public TagIdentifier get(String tagName) {
        if (tagNameSet.contains(tagName)) {
            return new SimpleTagIdentifier(tagSystemNameKey, tagName);
        } else {
            return null;
        }
    }
    
    public String getTagSystemNameKey() {
        return tagSystemNameKey;
    }
    
    public boolean owns(TagIdentifier tag) {
        Objects.requireNonNull(tag, "Tag cannot be null");
        
        // A tagSystem contains all the TagIdentifiers with the 
        // same tagSystemNameKey. 
        return tagSystemNameKey.equals(tag.getTagSystemNameKey());
    }
    
    public Map<String, Map<String, String>> remove(TagIdentifier tag) {
        if (owns(tag)) {
            String tagNameKey = tag.getTagNameKey();
            
            // Remove the tag from the cache.
            tagNameSet.remove(tagNameKey);
            
            // Remove any association with the tag.
            Map<String, Map<String, String>> removed = entitySystem.remove(tag);
            
            // Remove the tag itself.
            String value = statementStore.removeWithDescriptor(tagSystemNameKey, tagNameKey);
            removed.computeIfAbsent(tagSystemNameKey, k -> new HashMap<>())
                   .put(tagNameKey, value);
            
            return removed;
        }
        
        return null;
    }
    
    public Set<String> tagNameSet() {
        return Collections.unmodifiableSet(tagNameSet);
    }
    
    public Set<TagIdentifier> tagSet() {
        return tagNameSet
                .stream()
                .map(tagName -> new SimpleTagIdentifier(tagSystemNameKey, tagName))
                .collect(Collectors.toUnmodifiableSet());
    }
    
    private static class SimpleTagIdentifier implements TagIdentifier {
        private final String tagSystemNameKey;
        private final String tagNameKey;
        
        SimpleTagIdentifier(String tagSystemNameKey, String tagNameKey) {
            this.tagSystemNameKey = tagSystemNameKey;
            this.tagNameKey = tagNameKey;
        }
        
        public String asKey() {
            return Keys.combine(tagSystemNameKey, tagNameKey);
        }
        
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof SimpleTagIdentifier)) {
                return false;
            }
            SimpleTagIdentifier o = (SimpleTagIdentifier) other;
            return tagNameKey.equals(o.tagNameKey) &&
                   tagSystemNameKey.equals(o.tagSystemNameKey);
        }
        
        public String getTagNameKey() {
            return tagNameKey;
        }
        
        public String getTagSystemNameKey() {
            return tagSystemNameKey;
        }
        
        public int hashCode() {
            return Objects.hash(tagSystemNameKey, tagNameKey);
        }
        
        public String toString() {
            return String.format("SimpleTag: %s", asKey());
        }
    }
}
