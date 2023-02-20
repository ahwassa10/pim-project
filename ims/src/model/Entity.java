package model;

import java.util.UUID;

public class Entity {
	private UUID id;
	
	Entity() {
		id = UUID.randomUUID();
	}
	
	public UUID getID() {
		return this.id;
	}
}
