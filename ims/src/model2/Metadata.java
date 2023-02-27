package model2;

import model.real.PropertyName;

public interface Metadata<T> {
	
	public PropertyName getName();
	
	public T getValue();
}
