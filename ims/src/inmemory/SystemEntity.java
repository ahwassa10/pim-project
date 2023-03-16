package inmemory;

import information.identity.UUIDIdentity;

public abstract class SystemEntity implements Entity {
	private UUIDIdentity identity;
	
	SystemEntity(UUIDIdentity identity) {
		this.identity = identity;
	}
	
	public UUIDIdentity getIdentity() {
		return identity;
	}
}