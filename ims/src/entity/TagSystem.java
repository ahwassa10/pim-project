package entity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import statement.StatementStore;


public final class TagSystem {
    private final StatementStore statementStore;
    private final Set<String> tagNameSet = new HashSet<>();
    private final String tagSystemNameKey;
    
    TagSystem(StatementStore statementStore, String tagSystemNameKey) {
        this.statementStore = statementStore;
        this.tagSystemNameKey = tagSystemNameKey;
        initTagSet();
    }
    
    public void add(Tag tag) {
        Objects.requireNonNull(tag, "Tag cannot be null");
        
        if (!tagSystemNameKey.equals(tag.getTagSystemNameKey())) {
            String msg = String.format("%s is not from the TagSystem: %s",
                    tag.asKey(), tagSystemNameKey);
            throw new IllegalArgumentException(msg);
        }
        
        String tagNameKey = tag.getNameKey();
        if (tagNameSet.contains(tagNameKey)) {
            String msg = String.format("%s already in TagSystem: %s",
                    tag.asKey(), tagSystemNameKey);
            throw new IllegalArgumentException(msg);
        }
        
        tagNameSet.add(tagNameKey);
        statementStore.putDescriptor(tagSystemNameKey, tagNameKey);
    }
    
    public boolean contains(Tag tag) {
        Objects.requireNonNull(tag, "Tag cannot be null");
        
        String tagNameKey = tag.getNameKey();
        return tagNameSet.contains(tagNameKey);
    }
    
    public String getTagSystemNameKey() {
        return tagSystemNameKey;
    }
    
    private void initTagSet() {
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
            
            if (!Tags.isValidName(onDiskTag)) {
                String msg = String.format("%s.%s is not a valid tag",
                        tagSystemNameKey, onDiskTag);
                System.out.println(msg);
            }
            
            tagNameSet.add(onDiskTag);
        }
    }
    
    public boolean remove(Tag tag) {
        Objects.requireNonNull(tag, "Tag cannot be null");
        
        if (!tagSystemNameKey.equals(tag.getTagSystemNameKey())) {
            String msg = String.format("%s is not from the TagSystem: %s",
                    tag.asKey(), tagSystemNameKey);
            throw new IllegalArgumentException(msg);
        }
        
        String tagNameKey = tag.getNameKey();
        if (!tagNameSet.remove(tagNameKey)) {
            return false;
        } else {
            statementStore.remove(tagSystemNameKey, tagNameKey);
            return true;
        }
    }
    
    public Set<String> tagNameSet() {
        return Collections.unmodifiableSet(tagNameSet);
    }
    
    public Set<Tag> tagSet() {
        return tagNameSet.stream()
                         .map(tagName -> Tags.from(tagSystemNameKey, tagName))
                         .collect(Collectors.toUnmodifiableSet());
    }
}
