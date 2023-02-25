package ims.inmemory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.entities.Entity;

public class MemTable {
	private Map<Entity, Set<Entity>> memTable = new HashMap<>();
	
	public MemTable() {
		MemEntity.owner = this;
	}
	
	public void addRelationship(Entity subject, Entity object) {
		if (subject == null || object == null) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}
		
		memTable.get(subject).add(object);
		memTable.get(object).add(subject);
	}
	
	Entity addEntity(Entity subject) {
		if (!memTable.containsKey(subject)) {
			memTable.put(subject, new HashSet<>());
		}
		return subject;
	}
	
	public EntityCreator createEntity() {
		return new EntityCreator(this);
	}
	
	
	Set<Entity> getEntities() {
		return memTable.keySet();
	}
	
	Set<Entity> getRelationship(Entity subject) {
		return memTable.get(subject);
	}
	
	public void removeEntity(Entity subject) {
		if (subject == null) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}
		
		for (Entity object : memTable.get(subject)) {
			memTable.get(object).remove(subject);
		}
		
		memTable.remove(subject);
	}
	
	public void removeRelationship(Entity subject, Entity object) {
		if (subject == null || object == null) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}
		
		memTable.get(subject).remove(object);
		memTable.get(object).remove(subject);
	}
}
