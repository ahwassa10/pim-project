package ims.inmemory;

import model.properties.Name;

class MemName extends MemEntity implements Name {
	private String name;
	
	MemName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String toString() {
		return name;
	}
}
