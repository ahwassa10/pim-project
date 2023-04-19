package qualitygardens.taggarden;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import entity.EntitySystem;
import entity.Keys;
import statement.StatementStore;

public final class TagGarden {
    private final EntitySystem entitySystem;
    private final StatementStore statementStore;
    private final String tagGardenKey;
    
    private final String regularTagTypeKey;
    private final String selectionTagTypeKey;
    
    TagGarden(EntitySystem entitySystem,
              StatementStore statementStore,
              String tagGardenKey) {
        this.entitySystem = entitySystem;
        this.statementStore = statementStore;
        this.tagGardenKey = tagGardenKey;
        
        regularTagTypeKey = Keys.combine(tagGardenKey, "tag");
        selectionTagTypeKey = Keys.combine(tagGardenKey, "selection-tag");
    }
    
    public String associateSelectionTag(String tag_name,
                                        String tag_value,
                                        String key) {
        
        requireOwnedSelectionTag(tag_name);
        SelectionTags.requireValidSelectionTagValue(tag_value);
        Keys.requiresValidKey(key);
        
        String fullSelectionTagKey = Keys.combine(selectionTagTypeKey, tag_name);
        if (!statementStore.containsDescriptor(fullSelectionTagKey, tag_value)) {
            String msg = String.format("%s is not a valid option of %s",
                    tag_value, fullSelectionTagKey);
            throw new IllegalArgumentException(msg);
        }
        
        String selectionTagValue = getSelectionTagValue(tag_name, key);
        if (selectionTagValue != null) {
            String msg = String.format("%s is already associated with %s",
                    key, Keys.combine(fullSelectionTagKey, selectionTagValue));
            throw new IllegalArgumentException(msg);
        }
        
        String fullSelectionTagValueKey = Keys.combine(fullSelectionTagKey, tag_value);
        statementStore.putDescriptor(fullSelectionTagValueKey, key);
        
        return Keys.combine(fullSelectionTagValueKey, key);
    }
    
    public String associateTag(String tag_name, String key) {
        requireOwnedRegularTag(tag_name);
        Keys.requiresValidKey(key);
        
        String fullTagKey = Keys.combine(regularTagTypeKey, tag_name);
        statementStore.putDescriptor(fullTagKey, key);
        
        return Keys.combine(fullTagKey, key);
    }
    
    public void createSelectionTag(String tag_name, Set<String> values) {
        SelectionTags.requireValidSelectionTag(tag_name);
        Objects.requireNonNull(values, "Values set cannot be null");
        
        if (values.size() < 1) {
            throw new IllegalArgumentException("Value set must have at least one value");
        }
        
        if (statementStore.containsDescriptor(selectionTagTypeKey, tag_name)) {
            String msg = String.format("Selection-tag %s already exists in %s",
                    tag_name, tagGardenKey);
            throw new IllegalArgumentException(msg);
        }
        
        for (String value : values) {
            SelectionTags.requireValidSelectionTagValue(value);
        }
        
        statementStore.putDescriptor(selectionTagTypeKey, tag_name);
        String selectionTagKey = Keys.combine(selectionTagTypeKey, tag_name);
        for (String value : values) {
            statementStore.putDescriptor(selectionTagKey, value);
        }
    }
    
    public void createTag(String tag_name) {
        Tags.requireValidTag(tag_name);
        
        statementStore.putDescriptor(regularTagTypeKey, tag_name);
    }
    
    public Map<String, Map<String, String>> dissociateSelectionTag(String tag_name,
                                                                   String key) {
        requireOwnedSelectionTag(tag_name);
        Keys.requiresValidKey(key);
        
        String fullSelectionTagKey = Keys.combine(selectionTagTypeKey, tag_name);
        String selectionTagValue = getSelectionTagValue(tag_name, key);
        if (selectionTagValue != null) {
            String fullSelectionTagValueKey =
                    Keys.combine(fullSelectionTagKey, selectionTagValue);
            
            String association = Keys.combine(fullSelectionTagValueKey, key);
            
            // Remove all associations with the association
            Map<String, Map<String, String>> removed = entitySystem.remove(association);
            
            // Remove the association itself
            String value = statementStore.removeWithDescriptor(fullSelectionTagValueKey, key);
            removed.computeIfAbsent(fullSelectionTagValueKey, k -> new HashMap<>())
                   .put(key, value);
            
            return removed;
        }
        
        return new HashMap<>();
    }
    
    public Map<String, Map<String, String>> dissociateTag(String tag_name, String key) {
        requireOwnedRegularTag(tag_name);
        Keys.requiresValidKey(key);
        
        String fullTagKey = Keys.combine(regularTagTypeKey, tag_name);
        if (statementStore.containsDescriptor(fullTagKey, key)) {
            String association = Keys.combine(fullTagKey, tag_name);
            
            // Remove all associations with the association
            Map<String, Map<String, String>> removed = entitySystem.remove(association);
            
            // Remove the association itself
            String value = statementStore.removeWithDescriptor(fullTagKey, key);
            removed.computeIfAbsent(fullTagKey, k -> new HashMap<>())
                   .put(key, value);
            
            return removed;
        }
        
        return new HashMap<>();
    }
    
    public Set<String> getSelectionTags() {
        return statementStore.getWithQualifier(selectionTagTypeKey).keySet();
    }
    
    public String getSelectionTagValue(String tag_name, String key) {
        Keys.requiresValidKey(key);
        
        String fullSelectionTagKey = Keys.combine(selectionTagTypeKey, tag_name);
        Map<String, String> qualities = statementStore.getWithHolder(key);
        for (Map.Entry<String, String> quality : qualities.entrySet()) {
            String qualifier = quality.getKey();
            
            if (qualifier.contains(fullSelectionTagKey)) {
                return Keys.getTip(qualifier);
            }
        }
        
        return null;
    }
    
    public Set<String> getSelectionTagValues(String tag_name) {
        SelectionTags.requireValidSelectionTag(tag_name);
        
        String fullSelectionTagKey = Keys.combine(selectionTagTypeKey, tag_name);
        return statementStore.getWithQualifier(fullSelectionTagKey).keySet();
    }
    
    public Set<String> getTags() {
        return statementStore.getWithQualifier(regularTagTypeKey).keySet();
    }
    
    public Map<String, Map<String, String>> removeSelectionTag(String tag_name) {
        requireOwnedSelectionTag(tag_name);
        
        String fullSelectionTagKey = Keys.combine(selectionTagTypeKey, tag_name);
        
        // Remove any associations with the selection tag.
        Map<String, Map<String, String>> removed = entitySystem.remove(fullSelectionTagKey);
        
        // Remove the selection tag itself
        String value = statementStore.removeWithDescriptor(selectionTagTypeKey, tag_name);
        removed.computeIfAbsent(selectionTagTypeKey, k -> new HashMap<>())
               .put(tag_name, value);
        
        return removed;
    }
    
    public Map<String, Map<String, String>> removeTag(String tag_name) {
        requireOwnedRegularTag(tag_name);
        
        String fullTagKey = Keys.combine(regularTagTypeKey, tag_name);
        
        // Remove any association with the tag.
        Map<String, Map<String, String>> removed = entitySystem.remove(fullTagKey);
        
        // Remove the tag itself
        String value = statementStore.removeWithDescriptor(regularTagTypeKey, tag_name);
        removed.computeIfAbsent(regularTagTypeKey, k -> new HashMap<>())
               .put(tag_name, value);
        
        return removed;
    }
    
    public String requireOwnedRegularTag(String tag_name) {
        if (Tags.isValidTag(tag_name) &&
            statementStore.containsDescriptor(regularTagTypeKey, tag_name)) {
            return tag_name;
        } else {
            String msg = String.format("%s is not a regular tag of %s",
                    tag_name, tagGardenKey);
            throw new IllegalArgumentException(msg);
        }
    }
    
    public String requireOwnedSelectionTag(String tag_name) {
        if (SelectionTags.isValidSelectionTag(tag_name) &&
            statementStore.containsDescriptor(selectionTagTypeKey, tag_name)) {
            return tag_name;
        } else {
            String msg = String.format("%s is not a selection-tag of %s",
                    tag_name, tagGardenKey);
            throw new IllegalArgumentException(msg);
        }
    }
    
}
