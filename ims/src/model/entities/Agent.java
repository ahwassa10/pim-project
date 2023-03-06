package model.entities;

import java.util.List;

import model.metadata.Metadata;

public interface Agent extends Entity {
	public List<Metadata<Object>> getAttributions();
}
