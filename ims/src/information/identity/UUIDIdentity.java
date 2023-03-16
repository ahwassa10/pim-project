package information.identity;

import java.util.UUID;

import information.SimpleInfoPair;
import information.InfoPair;

public final class UUIDIdentity implements Identity {
	private final UUID identity = UUID.randomUUID();
	
	public UUIDIdentity() {}
	
	public InfoPair<Identity> asInfoPair() {
		return new SimpleInfoPair<Identity>(Identity.asInfoType(), this);
	}
	
	public UUID getIdentifier() {
		return identity;
	}
	
	public String toString() {
		return identity.toString();
	}
}