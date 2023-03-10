package model.entities;

import information.InfoTypeName;
import util.Pair;

public final class Property<V> {
	private final Pair<InfoTypeName, V> pair;
	
	private Property(InfoTypeName qualityType, V propertyValue) {
		pair = Pair.of(qualityType, propertyValue);
	}
	
	public static <V> Property<V> from(InfoTypeName qualityType,
									   V propertyValue) {
		return new Property<V>(qualityType, propertyValue);
	}
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof Property<?>)) {return false;}
		
		Property<?> p = (Property<?>) o;
		return pair.equals(p.pair); 
	}
	
	public InfoTypeName getQualityType() {
		return pair.getKey();
	}
	
	public V getPropertyValue() {
		return pair.getValue();
	}
	
	public int hashCode() {
		return pair.hashCode();
	}
	
	public String toString() {
		return String.format("Property<%s, %s>",
				pair.getKey(), pair.getValue());
	}
	
}