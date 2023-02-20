package model;

import java.util.HashSet;
import java.util.Set;

public class IMS {
	private Set<Entity> entities = new HashSet<>();
	private Set<Tag> tags        = new HashSet<>();
	private Set<Name> names      = new HashSet<>();
	
	public Entity createEntity() {
		Entity newEntity = new Entity();
		entities.add(newEntity);
		return newEntity;
	}
	
	public Tag createTag(String tag_name) {
		if (Tag.isValidTagName(tag_name)) {
			Tag newTag = new Tag(tag_name);
			tags.add(newTag);
			return newTag;
		} else {
			throw new IllegalArgumentException("Can't use this tag name");
		}
	}
	
	public Name createName(String name) {
		Name newName = new Name(name);
		names.add(newName);
		return newName;
	}
	
	
}
