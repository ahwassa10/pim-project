package model.entities;

import model.qualities.QualityType;
import structural.Pair;

public final class Property<V> {
	private final Pair<QualityType, V> property;
	
	private Property(QualityType qualityType, V propertyValue) {
		property = Pair.of(qualityType, propertyValue);
	}
	
	public static <V> Property<V> from(QualityType qualityType,
									   V propertyValue) {
		return new Property<V>(qualityType, propertyValue);
	}
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof Property<?>)) {return false;}
		
		Property<?> p = (Property<?>) o;
		return property.equals(p.property); 
	}
	
	public QualityType getQualityType() {
		return property.getKey();
	}
	
	public V getPropertyValue() {
		return property.getValue();
	}
	
	public int hashCode() {
		return property.hashCode();
	}
	
	public String toString() {
		return property.toString();
	}
	
}
