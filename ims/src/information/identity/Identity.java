package information.identity;

import java.util.UUID;

import information.InfoPair;
import information.InfoType;
import information.InfoTypeName;
import information.SimpleInfoType;

public interface Identity {
	UUID getIdentifier();
	
	InfoPair<Identity> asInfoPair();
	
	static InfoType asInfoType() {
		return new SimpleInfoType(InfoTypeName.from("Identity"));
	}
}
