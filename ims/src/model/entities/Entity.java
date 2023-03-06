package model.entities;

import java.util.List;

import model.metadata.Metadata;

public interface Entity {
	public List<Metadata<Object>> getProperties();
}
