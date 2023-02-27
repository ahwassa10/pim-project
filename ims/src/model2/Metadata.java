package model2;

import model.real.MetadataName;

public interface Metadata<T> {
	
	public MetadataName getName();
	
	public T getValue();
}
