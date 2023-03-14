package quality;

import model.qualities.QualityType;
import structural.Name;

public class NameQuality extends AbstractQualityPair<Name> {
	private static QualityType qualityType =
			QualityType.from("name");
	
	public NameQuality(Name name) {
		super(name);
	}
	
	public QualityType getQualityType() {
		return qualityType;
	}
}
