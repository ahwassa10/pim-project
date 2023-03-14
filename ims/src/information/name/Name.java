package information.name;

import information.AbstractInfoPair;
import information.InfoType;
import information.InfoTypeName;

public class Name extends AbstractInfoPair<StringName>{
	private static InfoType infoType = new InfoType() {
		private static InfoTypeName infoTypeName =
				InfoTypeName.from("name");
		
		public InfoTypeName getInfoTypeName() {
			return infoTypeName;
		}
	};
	
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
