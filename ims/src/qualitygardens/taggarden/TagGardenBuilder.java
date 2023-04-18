package qualitygardens.taggarden;

import java.util.Map;
import java.util.Objects;

import entity.Keys;
import statement.StatementStore;

public final class TagGardenBuilder {
    private final StatementStore statementStore;
    private final String tagGardenKey;
    
    public TagGardenBuilder(StatementStore statementStore, String tagGardenKey) {
        Objects.requireNonNull(statementStore, "StatementStore cannot be null");
        Objects.requireNonNull(tagGardenKey, "TagGarden key cannot be null");
        
        this.statementStore = statementStore;
        this.tagGardenKey = tagGardenKey;
    }
    
    public TagGarden build() {
        verifyRegularTags();
        verifySelectionTags();
        
        return new TagGarden(tagGardenKey);
    }
    
    private void verifyRegularTags() {
        String qualifier = Keys.combine(tagGardenKey, "tag");
        Map<String, String> qualifications = statementStore.getWithQualifier(qualifier);
        
        for (Map.Entry<String, String> qualification : qualifications.entrySet()) {
            String tag = qualification.getKey();
            
            if (!Tags.isValidTag(tag)) {
                String invalidTag = Keys.combine(qualifier, tag);
                String msg1 = String.format("%s has an invalid tag name", invalidTag);
                System.out.println(msg1);
                
                statementStore.remove(qualifier, tag);
                String msg2 = String.format("Successfully removed %s", invalidTag);
                System.out.println(msg2);
                
                continue;
            }
        }
    }
    
    private void verifySelectionTags() {
        String qualifier = Keys.combine(tagGardenKey, "selection-tag");
        Map<String, String> qualifications = statementStore.getWithQualifier(qualifier);
        
        for (Map.Entry<String, String> qualification : qualifications.entrySet()) {
            String selectionTag = qualification.getKey();
            
            if (!SelectionTags.isValidSelectionTag(selectionTag)) {
                String invalidSelectionTag = Keys.combine(qualifier, selectionTag);
                String msg1 = String.format("%s has an invalid selection-tag name",
                        invalidSelectionTag);
                System.out.println(msg1);
                
                statementStore.remove(qualifier, selectionTag);
                String msg2 = String.format("Successfully removed %s",
                        invalidSelectionTag);
                System.out.println(msg2);
                
                continue;
            }
            
            String selectionTagKey = Keys.combine(qualifier, selectionTag);
            int valueCount = verifySelectionTagValues(selectionTagKey);
            
            if (valueCount < 1) {
                String msg1 = String.format("%s needs at least one value",
                        selectionTagKey);
                System.out.println(msg1);
                
                statementStore.remove(qualifier, selectionTag);
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
