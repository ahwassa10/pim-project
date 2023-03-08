package model.metadata;

import model.real.Keyword;
import model.real.InformationType;

public interface Tag extends Information {
	public default InformationType getName() {
		return InformationType.from("tag");
	}
	
	public Keyword getValue();
}
