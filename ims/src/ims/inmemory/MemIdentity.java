package ims.inmemory;

import java.util.UUID;

import model.Identity;

class MemIdentity extends MemEntity implements Identity {
	private UUID id = UUID.randomUUID();
	
	MemIdentity() {}
	
	public UUID getID() {
		return this.id;
	}
	
	public String toString() {
		return id.toString();
	}
}
