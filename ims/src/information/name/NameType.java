package information.name;

import information.InfoType;
import information.InfoTypeName;

public final class NameType implements InfoType {
	private static final InfoTypeName infoTypeName =
			InfoTypeName.from("name");
	
	public InfoTypeName getInfoTypeName() {
		return infoTypeName;
	}
}
