package information.name;

import information.InfoPair;
import information.InfoType;
import information.InfoTypeName;
import information.SimpleInfoType;

public interface Name {
	String getName();
	
	InfoPair<Name> asInfoPair();
	
	static InfoType asInfoType() {
		return new SimpleInfoType(InfoTypeName.from("Name"));
	}
}
