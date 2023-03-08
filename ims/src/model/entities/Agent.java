package model.entities;

import java.util.List;

import model.information.Information;

public interface Agent extends Entity {
	List<Information> getAttributes();
}
