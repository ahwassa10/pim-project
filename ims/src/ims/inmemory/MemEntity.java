package ims.inmemory;

import java.util.Set;

import model.entities.Entity;

class MemEntity implements Entity{
	static MemTable owner;
	
	MemEntity() {}
	
	public Set<Entity> getRelationships() {
		return MemEntity.owner.getRelationship(this);
	}
}
