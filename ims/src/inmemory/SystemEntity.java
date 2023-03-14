package inmemory;

import model.entities.Entity;
import structural.Identity;

public abstract class SystemEntity implements Entity {
	private Identity identity;
	
	SystemEntity(Identity identity) {
		this.identity = identity;
	}
	
	public Identity getIdentity() {
		return identity;
	}
}