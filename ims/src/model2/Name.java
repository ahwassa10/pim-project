package model2;

import model.real.NameValue;
import model.real.PropertyName;

public interface Name extends Metadata<NameValue>{
	public default PropertyName getName() {
		return new PropertyName("name");
	}
	
	public NameValue getValue();
}
