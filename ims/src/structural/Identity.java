package structural;

import java.util.UUID;

public final class Identity {
	private UUID identity = UUID.randomUUID();
	
	private Identity() {}
	
	public static Identity newIdentifier() {
		return new Identity();
	}
	
	public UUID getIdentifier() {
		return identity;
	}
	
	public String toString() {
		return identity.toString();
	}
}