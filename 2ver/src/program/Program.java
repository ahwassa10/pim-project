package program;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import model.entity.Content;
import model.entity.ContentCore;
import model.entity.Tag;
import model.table.Tables;
import model.table.Tables.NKSVTable;
import model.table.Tables.SKNVTable;

public final class Program {
    private final NKSVTable<ContentCore> contentTable = Tables.noKeySingleValueTable();
    private final Map<UUID, SKNVTable> regularTags = new HashMap<>();
    
    public void createContent() {
        ContentCore contentCore = new ContentCore("Test name", "Test description", Instant.now());
        contentTable.add(contentCore);
    }
    
    public List<Content> getContent() {
        return contentTable.keys()
                .stream()
                .map(rowKey -> new Content(rowKey, contentTable.get(rowKey).any()))
                .toList();
    }
    
    public void removeContent(Content content) {
        Objects.requireNonNull(content);
        
        if (!contentTable.keys().contains(content.getContentKey())) {
            String msg = "This content does not exist";
            throw new IllegalArgumentException(msg);
        }
        
        contentTable.remove(content.getContentKey());
        if (regularTags.containsKey(content.getContentKey())) {
            regularTags.remove(content.getContentKey());
        }
    }
    
    public void createTag() {
        UUID newTag = contentTable.add(new ContentCore("Tag", "Tag Description", Instant.now()));
        String newTagName = "Tag " + newTag.toString().substring(9, 13);
        contentTable.replace(newTag, new ContentCore(newTagName, "Tag Description", Instant.now()));
        
        SKNVTable tagTable = Tables.singleKeyNoValueTable(newTag, contentTable);
        regularTags.put(newTag, tagTable);
    }

    public List<Tag> getTags() {
        return regularTags.keySet().stream()
            .map(tagKey -> new Tag(tagKey, contentTable.get(tagKey).any()))
            .toList();
    }
    
    public void removeTag(Tag tag) {
        Objects.requireNonNull(tag);
        
        if (!regularTags.containsKey(tag.getContentKey())) {
            String msg = "This tag does not exist";
            throw new IllegalArgumentException(msg);
        }
        
        contentTable.remove(tag.getContentKey());
        regularTags.remove(tag.getContentKey());
    }
    
    public void createTagApplication(Content content, Tag tag) {
        Objects.requireNonNull(content);
        Objects.requireNonNull(tag);
        
        if (!contentTable.keys().contains(content.getContentKey())) {
            String msg = "This content does not exist";
            throw new IllegalArgumentException(msg);
        }
        
        if (!regularTags.containsKey(tag.getContentKey())) {
            String msg = "This tag does not exist";
            throw new IllegalArgumentException(msg);
        }
        
        regularTags.get(tag.getContentKey()).add(content.getContentKey());
    }

    public List<Tag> getTagsFor(Content content) {
        Objects.requireNonNull(content);
        
        if (!contentTable.keys().contains(content.getContentKey())) {
            String msg = "This content does not exist";
            throw new IllegalArgumentException(msg);
        }
        
        return regularTags.keySet()
            .stream()
            .filter(tagKey -> regularTags.get(tagKey).keys().contains(content.getContentKey()))
            .map(tagKey -> new Tag(tagKey, contentTable.get(tagKey).any()))
            .toList();
    }
    
    public List<Content> getContentFor(Tag tag) {
        Objects.requireNonNull(tag);
        
        if (!regularTags.containsKey(tag.getContentKey())) {
            String msg = "This tag does not exist";
            throw new IllegalArgumentException(msg);
        }
        
        return regularTags.get(tag.getContentKey())
                .keys().stream()
                .map(rowKey -> new Content(rowKey, contentTable.get(rowKey).any()))
                .toList();
    }
    
    public void removeTagApplication(Content content, Tag tag) {
        Objects.requireNonNull(content);
        Objects.requireNonNull(tag);
        
        if (!regularTags.containsKey(tag.getContentKey())) {
            String msg = "This tag does not exist";
            throw new IllegalArgumentException(msg);
        }
        
        if (!regularTags.get(tag.getContentKey()).keys().contains(content.getContentKey())) {
            String msg = "This content is not tagged with this tag";
            throw new IllegalArgumentException(msg);
        }
        
        regularTags.get(tag.getContentKey()).remove(content.getContentKey());
    }
}