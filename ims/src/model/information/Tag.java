package model.information;

import model.real.Keyword;
import model.real.QualityName;

public interface Tag extends InformationType {
	public default QualityName getName() {
		return QualityName.from("tag");
	}
	
	public Keyword getValue();
}
