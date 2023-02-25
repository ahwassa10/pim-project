package inmemory;

import java.util.UUID;

import model.properties.Identity;

public class MemIdentity implements Identity {
	private UUID uuid;
	
	public UUID getIdentity() {
		return this.uuid;
	}
}
