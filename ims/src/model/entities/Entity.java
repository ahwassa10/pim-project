package model.entities;

import java.util.List;
import java.util.UUID;

import model.properties.Property;

public interface Entity {
	public UUID getUUID();
	
	public List<Property> getProperties();
}
