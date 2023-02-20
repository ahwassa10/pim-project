package model;

public class Name extends Entity {
	private String name;
	
	Name(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
