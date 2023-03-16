package inmemory;

import information.Identity;

public abstract class SystemEntity implements Entity {
	private Identity identity;
	
	SystemEntity(Identity identity) {
		this.identity = identity;
	}
	
	public Identity getIdentity() {
		return identity;
	}
}