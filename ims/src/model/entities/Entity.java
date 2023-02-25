package model.entities;

import java.util.List;
import java.util.Set;

import model.properties.Property;

public interface Entity {
	public Set<Link> getLinks();
	
	public List<Property> getProperties();
}
