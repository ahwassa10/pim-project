package inmemory;

import java.util.UUID;

public class MEntity {
	private UUID id;
	
	public MEntity() {
		id = UUID.randomUUID();
	}
	
	public UUID getID() {
		return this.id;
	}
}
