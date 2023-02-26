package model.properties;

public interface Keyword extends Property {
	public String getKeyword();
	
	public default String getPropertyName() {
		return "Keyword";
	}
	
	public default String getPropertyValue() {
		return getKeyword();
	}
}
