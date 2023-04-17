package qualitygardens.selectiontag;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import entity.EntitySystem;
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
    
}
