package information.name;

import information.InfoPair;
import information.InfoType;
import information.InfoTypeName;

public interface Name {
	String getName();
	
	InfoPair<Name> asInfoPair();
	
	static InfoType asInfoType() {
		return new InfoType() {
			// This instance is reused every time asInfoType() is called.
			private static final InfoTypeName infoTypeName =
					InfoTypeName.from("Name");
			
			public InfoTypeName getInfoTypeName() {
				return infoTypeName;
			}
		};
	}
}
