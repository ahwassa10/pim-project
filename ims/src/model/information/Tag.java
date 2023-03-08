package model.information;

import model.real.Keyword;
import model.real.InformationName;

public interface Tag extends Information {
	public default InformationName getName() {
		return InformationName.from("tag");
	}
	
	public Keyword getValue();
}
