package inmemory;

import model.entities.Entity;
import model.qualities.Identity;

public abstract class SystemEntity implements Entity {
	private Identity identity;
	
	SystemEntity(Identity identity) {
		this.identity = identity;
	}
	
	public Identity getIdentity() {
		return identity;
	}
}