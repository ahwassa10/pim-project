package qualities.naming;

import qualities.AbstractQualityPair;
import qualities.QualityName;

public class NameQuality extends AbstractQualityPair<StringName> {
	private static QualityName qualityType =
			QualityName.from("name");
	
	public NameQuality(StringName name) {
		super(name);
	}
	
	public QualityName getQualityType() {
		return qualityType;
	}
}
