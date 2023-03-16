package information.identity;

import java.util.UUID;

import information.InfoPair;
import information.InfoType;
import information.InfoTypeName;

public interface Identity {
	UUID getIdentifier();
	
	InfoPair<Identity> asInfoPair();
	
	static InfoType asInfoType() {
		return new InfoType() {
			private static final InfoTypeName infoTypeName =
					InfoTypeName.from("Identity");
			
			public InfoTypeName getInfoTypeName() {
				return infoTypeName;
			}
		};
	}
}
