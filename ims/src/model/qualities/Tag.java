package model.qualities;

import model.real.Keyword;

public interface Tag extends InformationType {
	public default QualityType getName() {
		return QualityType.from("tag");
	}
	
	public Keyword getValue();
}
