package model.entities;

import java.util.List;

import model.qualities.InformationType;

public interface Agent extends Entity {
	List<InformationType> getAttributes();
}
