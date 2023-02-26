package model.properties;

public interface Name extends Property {
	public String getName();
	
	public default String getPropertyName() {
		return "name";
	}
	
	public default String getPropertyValue() {
		return getName();
	}
}
