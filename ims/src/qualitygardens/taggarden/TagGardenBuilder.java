package qualitygardens.taggarden;

import java.util.Map;
import java.util.Objects;

import entity.EntitySystem;
import entity.Keys;
import statement.StatementStore;

public final class TagGardenBuilder {
    private final EntitySystem entitySystem;
    private final StatementStore statementStore;
    private final String tagGardenKey;
    
    public TagGardenBuilder(EntitySystem entitySystem, String tagGardenKey) {
        Objects.requireNonNull(entitySystem, "EntitySystem cannot be null");
        Keys.requiresValidKey(tagGardenKey);
        
        this.entitySystem = entitySystem;
        this.statementStore = entitySystem.getStatementStore();
        this.tagGardenKey = tagGardenKey;
    }
    
    public TagGarden build() {
        verifyRegularTags();
        verifySelectionTags();
        
        return new TagGarden(entitySystem, statementStore, tagGardenKey);
    }
    
    private void verifyRegularTags() {
        String tagTypeKey = Keys.combine(tagGardenKey, "tag");
        Map<String, String> qualifications = statementStore.getWithQualifier(tagTypeKey);
        
        for (Map.Entry<String, String> qualification : qualifications.entrySet()) {
            String tag = qualification.getKey();
            
            if (!Tags.isValidTag(tag)) {
                String invalidTag = Keys.combine(tagTypeKey, tag);
                String msg1 = String.format("%s has an invalid tag name", invalidTag);
                System.out.println(msg1);
                
                statementStore.remove(tagTypeKey, tag);
                String msg2 = String.format("Successfully removed %s", invalidTag);
                System.out.println(msg2);
                
                continue;
            }
        }
    }
    
    private void verifySelectionTags() {
        String tagTypeKey = Keys.combine(tagGardenKey, "selection-tag");
        Map<String, String> qualifications = statementStore.getWithQualifier(tagTypeKey);
        
        for (Map.Entry<String, String> qualification : qualifications.entrySet()) {
            String selectionTag = qualification.getKey();
            
            if (!SelectionTags.isValidSelectionTag(selectionTag)) {
                String invalidSelectionTag = Keys.combine(tagTypeKey, selectionTag);
                String msg1 = String.format("%s has an invalid selection-tag name",
                        invalidSelectionTag);
                System.out.println(msg1);
                
                statementStore.remove(tagTypeKey, selectionTag);
                String msg2 = String.format("Successfully removed %s",
                        invalidSelectionTag);
                System.out.println(msg2);
                
                continue;
            }
            
            String selectionTagKey = Keys.combine(tagTypeKey, selectionTag);
            int valueCount = verifySelectionTagValues(selectionTagKey);
            
            if (valueCount < 1) {
                String msg1 = String.format("%s needs at least one value",
                        selectionTagKey);
                System.out.println(msg1);
                
                statementStore.remove(tagTypeKey, selectionTag);
                String msg2 = String.format("Sucessfully removed %s",
                        selectionTagKey);
                System.out.println(msg2);
            }
        }
    }
    
    private int verifySelectionTagValues(String selectionTagKey) {
        int valueCount = 0;
        Map<String, String> qualifications = statementStore.getWithQualifier(selectionTagKey);
        
        for (Map.Entry<String, String> qualification : qualifications.entrySet()) {
            String selectionTagValue = qualification.getKey();
            
            if (!SelectionTags.isValidSelectionTagValue(selectionTagValue)) {
                String invalidSelectionTagValue = Keys.combine(selectionTagKey, selectionTagValue);
                String msg1 = String.format("%s is an invalid selection-tag value",
                        invalidSelectionTagValue);
                System.out.println(msg1);
                
                statementStore.remove(selectionTagKey, selectionTagValue);
                String msg2 = String.format("Successfully removed %s",
                        invalidSelectionTagValue);
                System.out.println(msg2);
                
                continue;
            }
            
            valueCount++;
        }
        
        return valueCount;
    }
}
