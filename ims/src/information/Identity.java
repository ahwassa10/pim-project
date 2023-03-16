package information;

import java.util.UUID;

public final class Identity implements Info {
	private static final InfoType INFO_TYPE =
			new SimpleInfoType(InfoTypeName.from("Identity"));
	
	private final UUID identity = UUID.randomUUID();
	
	public Identity() {}
	
	public InfoPair<Identity> asInfoPair() {
		return new SimpleInfoPair<Identity>(INFO_TYPE, this);
	}
	
	public UUID getIdentifier() {
		return identity;
	}
	
	public InfoType getInfoType() {
		return INFO_TYPE;
	}
	
	public String toString() {
		return identity.toString();
	}
}