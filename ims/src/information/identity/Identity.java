package information.identity;

import information.AbstractInfoPair;
import information.AbstractInfoType;
import information.InfoType;
import information.InfoTypeName;

public final class Identity extends AbstractInfoPair<UUIDIdentity> {
	private static InfoType infoType = new AbstractInfoType() {
		private static InfoTypeName infoTypeName =
				InfoTypeName.from("identity");
		
		public InfoTypeName getInfoTypeName() {
			return infoTypeName;
		}
	};
	
	private Identity(UUIDIdentity identity) {
		super(identity);
	}
	
	public static Identity newIdentifier() {
		return new Identity(UUIDIdentity.newIdentifier());
	}
	
	public InfoType getInfoType() {
		return infoType;
	}
	
	public String toString() {
		return String.format("Identity<%s>", this.getValue());
	}
}
