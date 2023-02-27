package model2;

import model.real.NameValue;

public interface Name extends Metadata<NameValue>{
	public default String getName() {
		return "name";
	}
	
	public NameValue getValue();
}
