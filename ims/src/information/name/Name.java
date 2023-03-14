package information.name;

import information.AbstractInfoPair;
import information.InfoType;

public class Name extends AbstractInfoPair<StringName>{
	private static InfoType infoType = new NameType();
	
	private Name(StringName name) {
		super(name);
	}
	
	public Name from(String name) {
		return new Name(StringName.from(name));
	}
	
	public InfoType getInfoType() {
		return infoType;
	}
}
