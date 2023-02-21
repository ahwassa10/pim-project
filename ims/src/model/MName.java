package model;

public class MName extends MEntity {
	private String name;
	
	MName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
