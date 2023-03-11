package model.entities;

import model.qualities.QualityType;

public final class Property<V> {
	private final Pair<QualityType, V> quality;
	
	private Property(QualityType qualityType, V propertyValue) {
		quality = Pair.of(qualityType, propertyValue);
	}
	
	public static <V> Property<V> from(QualityType qualityType, V propertyValue) {
		return new Property<V>(qualityType, propertyValue);
	}
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof Property<?>)) {return false;}
		
		Property<?> p = (Property<?>) o;
		return quality.equals(p.quality); 
	}
	
	public QualityType getQualityType() {
		return quality.getKey();
	}
	
	public V getQualityValue() {
		return quality.getValue();
	}
	
	public int hashCode() {
		return quality.hashCode();
	}
	
}
