package model;

public class Entity {
	private String name;
	private String value;
	
	public Entity(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}
}
