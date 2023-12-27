package program;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import model.entity.Content;
import model.entity.ContentCore;
import model.entity.Tag;
import model.entity.TagCore;
import model.table.Tables;
import model.table.Tables.NKSVTable;
import model.table.Tables.TKNVTable;
import model.util.UUIDs;

public final class Program {
    private final NKSVTable<ContentCore> contentTable = Tables.noKeySingleValueTable();
    private final NKSVTable<TagCore> tagTable = Tables.noKeySingleValueTable();
    private final TKNVTable tagApplicationTable = Tables.twoKeyNoValueTable(contentTable, tagTable);
    
    public void createContent() {
        ContentCore contentCore = new ContentCore("Test name", "Test description");
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
    }
    
    public List<Tag> getTags() {
        return tagTable.keys()
                .stream()
                .map(rowKey -> new Tag(rowKey, tagTable.get(rowKey).any()))
                .toList();
    }
    
    public void createTag() {
        TagCore tagCore = new TagCore("Test tag name");
        tagTable.add(tagCore);
    }
    
    public void removeTag(Tag tag) {
        Objects.requireNonNull(tag);
        
        if (!tagTable.keys().contains(tag.getTagKey())) {
            String msg = "This tag does not exist";
            throw new IllegalArgumentException(msg);
        }
        
        tagTable.remove(tag.getTagKey());
    }
    
    public List<Tag> getTagsFor(Content content) {
        Objects.requireNonNull(content);
        
        if (!contentTable.keys().contains(content.getContentKey())) {
            String msg = "This content does not exist";
            throw new IllegalArgumentException(msg);
        }
        
        UUID contentKey = content.getContentKey();
        return tagTable.keys()
                .stream()
                .filter(tagKey -> tagApplicationTable.keys().contains(UUIDs.xorUUIDs(tagKey, contentKey)))
                .map(tagKey -> new Tag(tagKey, tagTable.get(tagKey).any()))
                .toList();
    }
    
    public List<Content> getContentFor(Tag tag) {
        Objects.requireNonNull(tag);
        
        if (!tagTable.keys().contains(tag.getTagKey())) {
            String msg = "This tag does not exist";
            throw new IllegalArgumentException(msg);
        }
        
        UUID tagKey = tag.getTagKey();
        return contentTable.keys()
                .stream()
                .filter(contentKey -> tagApplicationTable.keys().contains(UUIDs.xorUUIDs(contentKey, tagKey)))
                .map(contentKey -> new Content(contentKey, contentTable.get(contentKey).any()))
                .toList();
    }
    
    public void createTagApplication(Content content, Tag tag) {
        Objects.requireNonNull(content);
        Objects.requireNonNull(tag);
        
        if (!contentTable.keys().contains(content.getContentKey())) {
            String msg = "This content does not exist";
            throw new IllegalArgumentException(msg);
        }
        
        if (!tagTable.keys().contains(tag.getTagKey())) {
            String msg = "This tag does not exist";
            throw new IllegalArgumentException(msg);
        }
        
        UUID applicationKey = UUIDs.xorUUIDs(content.getContentKey(), tag.getTagKey());
        
        if (tagApplicationTable.keys().contains(applicationKey)) {
            String msg = "This content already has this tag";
            throw new IllegalArgumentException(msg);
        }
        
        tagApplicationTable.add(content.getContentKey(), tag.getTagKey());
    }
    
    public void removeTagApplication(Content content, Tag tag) {
        Objects.requireNonNull(content);
        Objects.requireNonNull(tag);
        
        UUID applicationKey = UUIDs.xorUUIDs(content.getContentKey(), tag.getTagKey());
        if (!tagApplicationTable.keys().contains(applicationKey)) {
            String msg = "This tag application does not exist";
            throw new IllegalArgumentException(msg);
        }
        
        tagApplicationTable.remove(applicationKey);
    }
}