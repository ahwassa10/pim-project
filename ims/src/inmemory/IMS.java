package inmemory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class IMS {
	private Set<MEntity> entities = new HashSet<>();
	private Set<MTag> tags        = new HashSet<>();
	private Set<MName> names      = new HashSet<>();
	private HashMap<MTag, Set<MEntity>> taggedEntities  = new HashMap<>();
	
	public MEntity createEntity() {
		MEntity newEntity = new MEntity();
		entities.add(newEntity);
		return newEntity;
	}
	
	public MTag createTag(String tag_name) {
		if (MTag.isValidTagName(tag_name)) {
			MTag newTag = new MTag(tag_name);
			tags.add(newTag);
			return newTag;
		} else {
			throw new IllegalArgumentException("Can't use this tag name");
		}
	}
	
	public MName createName(String name) {
		MName newName = new MName(name);
		names.add(newName);
		return newName;
	}
	
	public void addTagToEntity(MTag t, MEntity e) {
		if (!taggedEntities.containsKey(t)) {
			taggedEntities.put(t, new HashSet<MEntity>());
		}
		taggedEntities.get(t).add(e);
	}
	
	public Set<MEntity> getEntitiesByTag(MTag t) {
		if (taggedEntities.containsKey(t)) {
			return taggedEntities.get(t);
		} else {
			return null;
		}
	}
}
