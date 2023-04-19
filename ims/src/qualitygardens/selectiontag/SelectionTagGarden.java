package qualitygardens.selectiontag;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import entity.EntitySystem;
import entity.Identifier;
import entity.Identifiers;
import entity.Keys;
import qualitygardens.taggarden.SelectionTags;
import statement.StatementStore;

public final class SelectionTagGarden {
    private final EntitySystem entitySystem;
    private final StatementStore statementStore;
    private final Map<String, Set<String>> selectionTagMap = new HashMap<>();
    private final String selectionTagGarden;
    
    public SelectionTagGarden(EntitySystem entitySystem, String selectionTagGarden) {
        this.entitySystem = entitySystem;
        this.statementStore = entitySystem.getStatementStore();
        this.selectionTagGarden = selectionTagGarden;
        
    }
    
    public boolean contains(String selectionTag) {
        return selectionTagMap.containsKey(selectionTag);
    }
    
    public Map<String, Map<String, String>> dissociate(SelectionTagValueID selectionTagValueID,
                                                       Identifier identifier) {
        Objects.requireNonNull(identifier, "Identifier cannot be null");
        
        if (!owns(selectionTagValueID)) {
            String msg = String.format("%s is not from the SelectionTagGarden: %s",
                    selectionTagValueID.asKey(), selectionTagGarden);
            throw new IllegalArgumentException(msg);
        }
        
        String qualifierKey = selectionTagValueID.asKey();
        String holderKey = identifier.asKey();
        
        if (statementStore.containsDescriptor(qualifierKey, holderKey)) {
            Identifier association = Identifiers.combine(selectionTagValueID, identifier);
            Map<String, Map<String, String>> removed = entitySystem.remove(association);
            
            String value = statementStore.removeWithDescriptor(qualifierKey, holderKey);
            removed.computeIfAbsent(qualifierKey, k -> new HashMap<>())
                   .put(holderKey, value);
            
            return removed;
        }
        
        return null;
    }
    
    public SelectionTagID get(String selectionTag) {
        if (selectionTagMap.containsKey(selectionTag)) {
            return new SimpleSelectionTagID(selectionTagGarden, selectionTag);
        }
        return null;
    }
    
    public SelectionTagValueID get(String selectionTag, String selectionTagValue) {
        SelectionTagID selectionTagID = get(selectionTag);
        if (selectionTagID != null &&
            selectionTagMap.get(selectionTag).contains(selectionTagValue)) {
            
            return new SimpleSelectionTagValueID(selectionTagID, selectionTagValue);
        } 
        return null;
    }
    
    public String getSelectionTagGarden() {
        return selectionTagGarden;
    }
    
    public boolean owns(SelectionTagID selectionTagID) {
        Objects.requireNonNull(selectionTagID, "Selection-tag ID cannot be null");
        
        return selectionTagGarden.equals(selectionTagID.getSelectionTagGarden());
    }
    
    public boolean owns(SelectionTagValueID selectionTagValueID) {
        Objects.requireNonNull(selectionTagValueID, "Selection-tag value ID cannot be null");
        
        String garden = selectionTagValueID.getSelectionTagID().getSelectionTagGarden();
        return selectionTagGarden.equals(garden);
    }
    
    public Map<String, Map<String, String>> remove(SelectionTagID selectionTagID) {
        if (owns(selectionTagID)) {
            String selectionTag = selectionTagID.getSelectionTag();
            
            // Remove the selection-tag from the cache.
            selectionTagMap.remove(selectionTag);
            
            // Remove any association with the selection-tag
            // Note that this also removes all the selection-tag values
            Map<String, Map<String, String>> removed = entitySystem.remove(selectionTagID);
            
            // Remove the tag itself
            String value = statementStore.removeWithDescriptor(selectionTagGarden, selectionTag);
            removed.computeIfAbsent(selectionTagGarden, k -> new HashMap<>())
                   .put(selectionTag, value);
            
            return removed;
        }
        
        return null;
    }
    
    public Set<String> selectionTagSet() {
        return Collections.unmodifiableSet(selectionTagMap.keySet());
    }
    
    public Set<String> selectionTagValueSet(String selectionTag) {
        Objects.requireNonNull(selectionTag, "Selection tag cannot be null");
        Set<String> selectionTagValues =
                selectionTagMap.getOrDefault(selectionTag, 
                                             Collections.emptySet());
        return Collections.unmodifiableSet(selectionTagValues);
    }
    
    private static class SimpleSelectionTagID
    implements SelectionTagID {
        private final String selectionTagGarden;
        private final String selectionTag;
        
        SimpleSelectionTagID(String selectionTagGarden, String selectionTag) {
            this.selectionTagGarden = selectionTagGarden;
            this.selectionTag = selectionTag;
        }
        
        public String asKey() {
            return Keys.combine(selectionTagGarden, selectionTag);
        }
        
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof SimpleSelectionTagID)) {
                return false;
            }
            
            SimpleSelectionTagID o = (SimpleSelectionTagID) other;
            return selectionTagGarden.equals(o.selectionTagGarden) &&
                   selectionTag.equals(o.selectionTag);
        }
        
        public String getSelectionTag() {
            return selectionTag;
        }
        
        public String getSelectionTagGarden() {
            return selectionTagGarden;
        }
        
        public int hashCode() {
            return Objects.hash(selectionTagGarden, selectionTag);
        }
        
        public String toString() {
            return String.format("SimpleSelectionTag: %s", asKey());
        }
    }
    
    private static class SimpleSelectionTagValueID
    implements SelectionTagValueID {
        private final SelectionTagID selectionTagID;
        private final String selectionTagValue;
        
        SimpleSelectionTagValueID(SelectionTagID selectionTagID,
                                          String selectionTagValue) {
            this.selectionTagID = selectionTagID;
            this.selectionTagValue = selectionTagValue;
        }
        
        public String asKey() {
            return Keys.combine(selectionTagID.asKey(), selectionTagValue);
        }
        
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof SimpleSelectionTagValueID)) {
                return false;
            }
            SimpleSelectionTagValueID o = (SimpleSelectionTagValueID) other;
            return selectionTagID.equals(o.selectionTagID) &&
                   selectionTagValue.equals(o.selectionTagValue);
        }
        
        public SelectionTagID getSelectionTagID() {
            return selectionTagID;
        }
        
        public String getSelectionTagValue() {
            return selectionTagValue;
        }
        
        public int hashCode() {
            return Objects.hash(selectionTagID, selectionTagValue);
        }
        
        public String toString() {
            return String.format("SimpleSelectionTagValue: %s", asKey());
        }
    }
}
