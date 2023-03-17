package information;

import java.util.UUID;

public final class Identity extends AbstractSingleValueInfo<UUID> {
	private static final InfoType INFO_TYPE =
			new SimpleInfoType("Identity");
	
	private Identity(UUID identity) {
		super(identity);
	}
	
	public static Identity newIdentifier() {
		return new Identity(UUID.randomUUID());
	}
	
	public InfoPair<Identity> asInfoPair() {
		return new SimpleInfoPair<Identity>(INFO_TYPE, this);
	}
	
	public InfoType getInfoType() {
		return INFO_TYPE;
	}
}