package program;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import model.entity.Content;
import model.entity.ContentCore;
import model.entity.Tag;
import model.memtable.RootSVTable;

public final class Program {
    private final RootSVTable<ContentCore> contentTable = RootSVTable.create();
    
    public void createContent() {
        contentTable.add(key -> new ContentCore("Content " + key.toString().substring(9, 13),
                                                "Test description",
                                                Instant.now()));
    }
    
    public List<Content> getContent() {
        return contentTable.keys()
                .stream()
                .map(key -> new Content(key, contentTable.get(key)))
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
    
    public void createTag() {
        UUID newTag = contentTable.add(key -> new ContentCore("Tag " + key.toString().substring(9, 13), "Tag description", Instant.now()));
        
        contentTable.createNVTable(newTag);
    }

    public List<Tag> getTags() {
        return contentTable.getSubTables().values()
                .stream()
                .map(table -> new Tag(table.getTableID(), contentTable.get(table.getTableID())))
                .toList();
    }
    
    public void removeTag(Tag tag) {
        Objects.requireNonNull(tag);
        
        UUID tagID = tag.getContentKey();
        
        if (!contentTable.getSubTables().containsKey(tagID)) {
            String msg = "This tag does not exist";
            throw new IllegalArgumentException(msg);
        }
        
        contentTable.remove(tagID);
    }
    
    public void createTagApplication(Content content, Tag tag) {
        Objects.requireNonNull(content);
        Objects.requireNonNull(tag);
        
        if (!contentTable.keys().contains(content.getContentKey())) {
            String msg = "This content does not exist";
            throw new IllegalArgumentException(msg);
        }
        
        if (!contentTable.keys().contains(tag.getContentKey())) {
            String msg = "This tag does not exist";
            throw new IllegalArgumentException(msg);
        }
        
        UUID tagID = tag.getContentKey();
        contentTable.getSubNVTables().get(tagID).add(content.getContentKey());
        
    }

    public List<Tag> getTagsFor(Content content) {
        Objects.requireNonNull(content);
        
        if (!contentTable.keys().contains(content.getContentKey())) {
            String msg = "This content does not exist";
            throw new IllegalArgumentException(msg);
        }
        
        return contentTable.getSubNVTables().values()
                .stream()
                .filter(table -> table.keys().contains(content.getContentKey()))
                .map(table -> new Tag(table.getTableID(), contentTable.get(table.getTableID())))
                .toList();
    }
    
    public List<Content> getContentFor(Tag tag) {
        Objects.requireNonNull(tag);
        
        if (!contentTable.keys().contains(tag.getContentKey())) {
            String msg = "This tag does not exist";
            throw new IllegalArgumentException(msg);
        }
        
        return contentTable.getSubNVTables().get(tag.getContentKey()).keys()
                .stream()
                .map(key -> new Content(key, contentTable.get(key)))
                .toList();
    }
}