package information.identity;

import java.util.UUID;

import information.AbstractInfoPair;
import information.InfoPair;
import information.InfoType;

public final class UUIDIdentity implements Identity {
	private final UUID identity = UUID.randomUUID();
	
	public UUIDIdentity() {}
	
	public InfoPair<Identity> asInfoPair() {
		return new AbstractInfoPair<Identity>(this) {
			private static InfoType infoType = Identity.asInfoType();
			
			public InfoType getInfoType() {
				return infoType;
			}
		};
	}
	
	public UUID getIdentifier() {
		return identity;
	}
	
	public String toString() {
		return identity.toString();
	}
}