package qualities;

import information.InfoTypeName;
import information.name.StringName;

public class NameQuality extends AbstractQualityPair<StringName> {
	private static InfoTypeName qualityType =
			InfoTypeName.from("name");
	
	public NameQuality(StringName name) {
		super(name);
	}
	
	public InfoTypeName getQualityType() {
		return qualityType;
	}
}
