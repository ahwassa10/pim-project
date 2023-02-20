package model;

import java.util.UUID;

public class Entity {
	private UUID id;
	
	public Entity() {
		id = UUID.randomUUID();
	}
	
	public UUID getID() {
		return this.id;
	}
}
