package qualitygardens.selectiontag;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import entity.EntitySystem;
import entity.Identifier;
import entity.Keys;
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
        
        initSelectionTagMap();
    }
    
    public boolean contains(String selectionTag) {
        return selectionTagMap.containsKey(selectionTag);
    }
    
    public SelectionTagID createAndAdd(String selectionTag, Set<String> selectionTagValues) {
        SelectionTags.requireValidSelectionTag(selectionTag);
        
        if (selectionTagMap.containsKey(selectionTag)) {
            String msg = String.format("%s already exists",
                    Keys.combine(selectionTagGarden, selectionTag));
            throw new IllegalArgumentException(msg);
        } 
        
        for (String selectionTagValue : selectionTagValues) {
            SelectionTags.requireValidSelectionTagValue(selectionTagValue);
        }
        
        statementStore.putDescriptor(selectionTagGarden, selectionTag);
        String selectionTagKey = Keys.combine(selectionTagGarden, selectionTag);
        
        for (String selectionTagValue : selectionTagValues) {
            statementStore.putDescriptor(selectionTagKey, selectionTagValue);
        }
        
        return new SimpleSelectionTagID(selectionTagGarden, selectionTag);
    }
    
    private void initSelectionTag(String selectionTag) {
        String selectionTagInstance = Keys.combine(selectionTagGarden, selectionTag);
        
        Map<String, String> selectionTagValues =
                statementStore.getWithQualifier(selectionTagInstance);
        
        for (Map.Entry<String, String> onDisk : selectionTagValues.entrySet()) {
            String selectionTagValue = onDisk.getKey();
            String value = onDisk.getValue();
            
            if (!value.equals("")) {
                String msg = String.format("%s should not have a value",
                        Keys.combine(selectionTagInstance, selectionTagValue));
                System.out.println(msg);
                continue;
            }
            
            if (!SelectionTags.isValidSelectionTagValue(selectionTagValue)) {
                String msg = String.format("%s is not a valid selection tag value",
                        Keys.combine(selectionTagInstance, selectionTagValue));
                System.out.println(msg);
                continue;
            }
            
            selectionTagMap.computeIfAbsent(selectionTag, st -> new HashSet<>())
                           .add(selectionTagValue);
        }
    }
    
    private void initSelectionTagMap() {
        Map<String, String> selectionTags =
                statementStore.getWithQualifier(selectionTagGarden);
        
        for (Map.Entry<String, String> onDisk : selectionTags.entrySet()) {
            String selectionTag = onDisk.getKey();
            String value = onDisk.getValue();
            
            if (!value.equals("")) {
                String msg = String.format("%s should not have a value",
                        Keys.combine(selectionTagGarden, selectionTag));
                System.out.println(msg);
                continue;
            }
            
            if (!SelectionTags.isValidSelectionTag(selectionTag)) {
                String msg = String.format("%s is not a valid selection tag",
                        Keys.combine(selectionTagGarden, selectionTag));
                System.out.println(msg);
                continue;
            }
            
            initSelectionTag(selectionTag);
            
            if (!selectionTagMap.containsKey(selectionTag)) {
                String msg = String.format("%s has no valid values",
                        Keys.combine(selectionTagGarden, selectionTag));
                System.out.println(msg);
            }
        }
    }
    
    public SelectionTagID get(String selectionTag) {
        if (selectionTagMap.containsKey(selectionTag)) {
            return new SimpleSelectionTagID(selectionTagGarden, selectionTag);
        } else {
            return null;
        }
    }
    
    public boolean owns(SelectionTagID selectionTagID) {
        Objects.requireNonNull(selectionTagID, "Selection-tag ID cannot be null");
        
        return selectionTagGarden.equals(selectionTagID.getSelectionTagGarden());
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
            return Keys.combine(selectionTag, selectionTagGarden);
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
