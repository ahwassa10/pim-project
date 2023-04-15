package entity;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import statement.StatementStore;


public final class TagSystem {
    private final StatementStore statementStore;
    private final Set<String> tagSet = new HashSet<>();
    private final String tagSystemQualifier;
    
    TagSystem(StatementStore statementStore, String tagSystemQualifier) {
        this.statementStore = statementStore;
        this.tagSystemQualifier = tagSystemQualifier;
        initTagSet();
    }
    
    public String add(String tag) {
        Tags.requireValidTag(tag);
        
        statementStore.putDescriptor(tagSystemQualifier, tag);
        tagSet.add(tag);
        
        return tag;
    }
    
    public boolean contains(String tag) {
        return tagSet.contains(tag);
    }
    
    private void initTagSet() {
        Map<String, String> onDiskTags = statementStore.getQualities(tagSystemQualifier);
        
        for (Map.Entry<String, String> entry : onDiskTags.entrySet()) {
            String onDiskTag = entry.getKey();
            String onDiskValue = entry.getValue();
            
            if (!onDiskValue.equals("")) {
                String msg = String.format("%s.%s should not have a value",
                        tagSystemQualifier, onDiskTag);
                System.out.println(msg);
                continue;
            }
            
            if (!Tags.isValid(onDiskTag)) {
                String msg = String.format("%s.%s is not a valid tag",
                        tagSystemQualifier, onDiskTag);
                System.out.println(msg);
            }
            
            tagSet.add(onDiskTag);
        }
    }
    
    public boolean remove(String tag) {
        if (!tagSet.contains(tag)) {
            Tags.requireValidTag(tag);
            return false;
        }
        
        statementStore.remove(tagSystemQualifier, tag);
        return true;
    }
}
