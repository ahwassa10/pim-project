package model.properties;

public interface Property<T> {
	public String getPropertyName();
	
	public T getPropertyValue();
}
