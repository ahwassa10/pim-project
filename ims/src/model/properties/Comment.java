package model.properties;

public interface Comment extends Property {
	public String getComment();
	
	public default String getPropertyName() {
		return "Comment";
	}
	
	public default String getPropertyValue() {
		return getComment();
	}
}
