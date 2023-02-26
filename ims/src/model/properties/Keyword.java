package model.properties;

public interface Keyword extends Property {
	public String getKeyword();
	
	public default String getPropertyName() {
		return "keyword";
	}
	
	public default String getPropertyValue() {
		return getKeyword();
	}
}