package ims.inmemory;

import java.util.ArrayList;
import java.util.List;

import model.Entity;
import model.Tag;

public class EntityCreator {
	private List<Entity> registrationQueue = new ArrayList<>();
	private final MemTable owner;
	
	EntityCreator(MemTable owner) {
		this.owner = owner;
		registrationQueue.add(new MemIdentity());
	}
	
	public Entity create() {
		Entity subject = new MemEntity();
		owner.addEntity(subject);
		
		for (Entity object : registrationQueue) {
			owner.addEntity(object);
			owner.addRelationship(subject, object);
		}
		return subject;
	}
	
	public EntityCreator setName(String name) {
		registrationQueue.add(new MemName(name));
		return this;
	}
	
	public EntityCreator addTag(Tag tag) {
		if (tag == null) {
			throw new IllegalArgumentException("Tag cannot be null");
		}
		
		registrationQueue.add(tag);
		
		return this;
	}
}
