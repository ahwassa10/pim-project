package model.entities;

import qualities.QualityName;
import util.Triple;

public class Attribute<V> {
	private final Triple<Entity, QualityName, V> triple;
	
	private Attribute(Entity entity,
					  QualityName qualityType,
					  V attributeValue) {
		triple = Triple.of(entity, qualityType, attributeValue);
	}
	
	public static <V> Attribute<V> from(Entity entity,
										QualityName qualityType,
										V attributeValue) {
		return new Attribute<V>(entity, qualityType, attributeValue);
	}
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof Attribute<?>)) {return false;}
		
		Attribute<?> a = (Attribute<?>) o;
		return (triple.equals(a.triple));
	}
	
	public Entity getEntity() {
		return triple.getKey();
	}
	
	public QualityName getQualityType() {
		return triple.getQualifier();
	}
	
	public V getAttributeValue() {
		return triple.getValue();
	}
	
	public int hashCode() {
		return triple.hashCode();
	}
	
	public String toString() {
		return triple.toString();
	}
}