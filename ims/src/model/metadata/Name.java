package model.metadata;

import model.real.NameValue;
import model.real.InformationType;

public interface Name extends Information {
	public default InformationType getName() {
		return InformationType.from("name");
	}
	
	public NameValue getValue();
}
