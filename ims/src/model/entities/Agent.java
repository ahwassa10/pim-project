package model.entities;

import java.util.List;

import model.information.InformationType;

public interface Agent extends Entity {
	List<InformationType> getAttributes();
}
