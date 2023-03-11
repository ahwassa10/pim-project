package model.entities;

import model.qualities.QualityType;

public class Attribute<V> {
	private final Triple<Entity, QualityType, V> attribute;
	
	private Attribute(Entity entity,
					  QualityType qualityType,
					  V attributeValue) {
		attribute = Triple.of(entity, qualityType, attributeValue);
	}
	
	public static <V> Attribute<V> from(Entity entity,
										QualityType qualityType,
										V attributeValue) {
		return new Attribute<V>(entity, qualityType, attributeValue);
	}
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof Attribute<?>)) {return false;}
		
		Attribute<?> a = (Attribute<?>) o;
		return (attribute.equals(a.attribute));
	}
	
	public Entity getEntity() {
		return attribute.getKey();
	}
	
	public QualityType getQualityType() {
		return attribute.getQualifier();
	}
	
	public V getAttributeValue() {
		return attribute.getValue();
	}
	
	public int hashCode() {
		return attribute.hashCode();
	}
}
