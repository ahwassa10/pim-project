package model.metadata;

import model.real.NameValue;
import model.real.MetadataName;

public interface Name extends Metadata<NameValue>{
	public default MetadataName getName() {
		return MetadataName.from("name");
	}
	
	public NameValue getValue();
}
