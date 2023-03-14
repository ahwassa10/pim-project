package information.identity;

import java.util.UUID;

public final class UUIDIdentity {
	private UUID identity = UUID.randomUUID();
	
	private UUIDIdentity() {}
	
	public static UUIDIdentity newIdentifier() {
		return new UUIDIdentity();
	}
	
	public UUID getIdentifier() {
		return identity;
	}
	
	public String toString() {
		return identity.toString();
	}
}