package model.properties;

public interface Name extends Property<String> {
	public String getName();
	
	public default String getPropertyName() {
		return "name";
	}
	
	public default String getPropertyValue() {
		return getName();
	}
}
