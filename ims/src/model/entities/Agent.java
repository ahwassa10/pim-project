package model.entities;

import java.util.List;

import model.metadata.Information;

public interface Agent extends Entity {
	List<Information> getAttributes();
}
