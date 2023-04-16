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
        initTagNameSet();
    }
    
    public Identifier associate(TagIdentifier tag, Identifier identifier) {
        Objects.requireNonNull(tag, "Tag cannot be null");
        Objects.requireNonNull(identifier, "Identifier cannot be null");
        
        if (!tagSystemNameKey.equals(tag.getTagSystemNameKey())) {
            String msg = String.format("%s is not from the TagSystem: %s",
                    tag.asKey(), tagSystemNameKey);
            throw new IllegalArgumentException(msg);
        }
        
        String qualifierKey = tag.asKey();
        String holderKey = identifier.asKey();
        statementStore.putDescriptor(qualifierKey, holderKey);
        
        return Identifiers.combine(tag, identifier);
    }
    
    public boolean contains(TagIdentifier tag) {
        Objects.requireNonNull(tag, "Tag cannot be null");
        
        // A tagSystem contains all the TagIdentifiers with the 
        // same tagSystemNameKey. 
        return tagSystemNameKey.equals(tag.getTagSystemNameKey());
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
        Objects.requireNonNull(tag, "Tag cannot be null");
        Objects.requireNonNull(identifier, "Identifier cannot be null");
        
        if (!tagSystemNameKey.equals(tag.getTagSystemNameKey())) {
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
            String value = statementStore.remove(qualifierKey, holderKey);
            removed.computeIfAbsent(qualifierKey, k -> new HashMap<>())
                   .put(holderKey, value);
            
            return removed;
        }
        
        return null;
    }
    
    public String getTagSystemNameKey() {
        return tagSystemNameKey;
    }
    
    private void initTagNameSet() {
        Map<String, String> onDiskTags = statementStore.getQualities(tagSystemNameKey);
        
        for (Map.Entry<String, String> entry : onDiskTags.entrySet()) {
            String onDiskTag = entry.getKey();
            String onDiskValue = entry.getValue();
            
            if (!onDiskValue.equals("")) {
                String msg = String.format("%s.%s should not have a value",
                        tagSystemNameKey, onDiskTag);
                System.out.println(msg);
                continue;
            }
            
            if (!TagIdentifiers.isValidTagName(onDiskTag)) {
                String msg = String.format("%s.%s is not a valid tag",
                        tagSystemNameKey, onDiskTag);
                System.out.println(msg);
            }
            
            tagNameSet.add(onDiskTag);
        }
    }
    
    public Map<String, Map<String, String>> remove(TagIdentifier tag) {
        Objects.requireNonNull(tag, "Tag cannot be null");
        
        if (tagSystemNameKey.equals(tag.getTagSystemNameKey())) {
            String tagNameKey = tag.getNameKey();
            
            // Remove from the cache
            tagNameSet.remove(tagNameKey);
            
            // Remove all associations with the tag.
            return entitySystem.remove(tag);
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
        private final String tagNameKey;
        private final String tagSystemKey;
        
        SimpleTagIdentifier(String tagNameKey, String tagSystemKey) {
            this.tagNameKey = tagNameKey;
            this.tagSystemKey = tagSystemKey;
        }
        
        public String asKey() {
            return Keys.combine(tagSystemKey, tagNameKey);
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
                    tagSystemKey.equals(o.tagSystemKey);
        }
        
        public String getNameKey() {
            return tagNameKey;
        }
        
        public String getTagSystemNameKey() {
            return tagSystemKey;
        }
        
        public int hashCode() {
            return Objects.hash(tagNameKey, tagSystemKey);
        }
        
        public String toString() {
            return String.format("SimpleTag: %s.%s", tagSystemKey, tagNameKey);
        }
    }
}
