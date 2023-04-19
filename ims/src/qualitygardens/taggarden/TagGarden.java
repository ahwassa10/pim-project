package qualitygardens.taggarden;

import java.util.HashMap;
import java.util.Map;
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
    
    public String associateSelectionTag(String fullSelectionTagValueKey, String key) {
        String fullSelectionTagKey = Keys.getStem(fullSelectionTagValueKey);
        
        String tagTypeKey = Keys.getStem(Keys.getStem(fullSelectionTagKey));
        requireOwnedSelectionTag(tagTypeKey);
        
        String selectionTag = Keys.getTip(fullSelectionTagKey);
        SelectionTags.requireValidSelectionTag(selectionTag);
        if (!statementStore.containsDescriptor(selectionTagTypeKey, selectionTag)) {
            String msg = String.format("%s does not exist", fullSelectionTagKey);
            throw new IllegalArgumentException(msg);
        }
        
        String selectionTagValue = Keys.getTip(fullSelectionTagValueKey);
        SelectionTags.requireValidSelectionTagValue(selectionTagValue);
        if (!statementStore.containsDescriptor(fullSelectionTagKey, selectionTagValue)) {
            String msg = String.format("%s is not a valid option of %s",
                    selectionTagValue, fullSelectionTagKey);
            throw new IllegalArgumentException(msg);
        }
        
        statementStore.putDescriptor(fullSelectionTagValueKey, key);
        
        return Keys.combine(fullSelectionTagValueKey, key);
        
    }
    
    public String associateTag(String fullTagKey, String key) {
        String tagTypeKey = Keys.getStem(fullTagKey);
        requireOwnedRegularTag(tagTypeKey);
        
        String tagKey = Keys.getTip(fullTagKey);
        Tags.requireValidTag(tagKey);
        
        Keys.requiresValidKey(key);
        
        statementStore.putDescriptor(regularTagTypeKey, tagKey);
        statementStore.putDescriptor(fullTagKey, key);
        
        return Keys.combine(tagKey, key);
    }
    
    public Map<String, Map<String, String>> dissociateTag(String fullTagKey, String key) {
        String tagTypeKey = Keys.getStem(fullTagKey);
        requireOwnedRegularTag(tagTypeKey);
        
        String tagKey = Keys.getTip(fullTagKey);
        Tags.requireValidTag(tagKey);
        
        Keys.requiresValidKey(key);
        
        String association = Keys.combine(fullTagKey, tagKey);
        // Remove the creation-time and other associations of the association.
        Map<String, Map<String, String>> removed = entitySystem.remove(association);
        
        String value = statementStore.removeWithDescriptor(fullTagKey, key);
        removed.computeIfAbsent(fullTagKey, k -> new HashMap<>())
               .put(key, value);
        
        return removed;
    }
    
    public String getTag(String tagKey) {
        Tags.requireValidTag(tagKey);
        
        return Keys.combine(regularTagTypeKey, tagKey);
    }
    
    public Set<String> getTags() {
        return statementStore.getWithQualifier(regularTagTypeKey).keySet();
    }
    
    public Map<String, Map<String, String>> removeTag(String fullTagKey) {
        String tagTypeKey = Keys.getStem(fullTagKey);
        requireOwnedRegularTag(tagTypeKey);
        
        String tagKey = Keys.getTip(fullTagKey);
        Tags.requireValidTag(tagKey);
        
        // Remove any association with the tag.
        Map<String, Map<String, String>> removed = entitySystem.remove(fullTagKey);
        
        // Remove the tag itself
        String value = statementStore.removeWithDescriptor(regularTagTypeKey, tagKey);
        removed.computeIfAbsent(regularTagTypeKey, k -> new HashMap<>())
               .put(tagKey, value);
        
        return removed;
    }
    
    public String requireOwnedRegularTag(String tagTypeKey) {
        if (regularTagTypeKey.equals(tagTypeKey)) {
            return tagTypeKey;
        } else {
            String msg = String.format("%s is not a regular tag of %s",
                    tagTypeKey, tagGardenKey);
            throw new IllegalArgumentException(msg);
        }
    }
    
    public String requireOwnedSelectionTag(String tagTypeKey) {
        if (selectionTagTypeKey.equals(tagTypeKey)) {
            return tagTypeKey;
        } else {
            String msg = String.format("%s is not a selection-tag of %s",
                    tagTypeKey, tagGardenKey);
            throw new IllegalArgumentException(msg);
        }
    }
    
}
