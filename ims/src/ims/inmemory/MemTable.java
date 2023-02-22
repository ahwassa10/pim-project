package ims.inmemory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.Entity;

public class MemTable {
	private Map<Entity, Set<Entity>> memTable = new HashMap<>();
	
	public void add(Entity entity) {
		if (entity == null) {
			throw new IllegalArgumentException("Entity cannot be null");
		}
		
		if (!memTable.containsKey(entity)) {
			memTable.put(entity, new HashSet<Entity>());
		}
	}
	
	public void addRel(Entity subject, Entity object) {
		if (subject == null || object == null) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}
		
		if (!memTable.containsKey(subject)) {
			memTable.put(subject, new HashSet<Entity>());
		}
		
		if (!memTable.containsKey(object)) {
			memTable.put(object, new HashSet<Entity>());
		}
		
		memTable.get(subject).add(object);
		memTable.get(object).add(subject);
	}
	
	public void removeRel(Entity subject, Entity object)
}
