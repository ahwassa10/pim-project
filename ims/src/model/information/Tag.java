package model.information;

import model.real.Keyword;
import model.real.InformationName;

public interface Tag extends InformationType {
	public default InformationName getName() {
		return InformationName.from("tag");
	}
	
	public Keyword getValue();
}
