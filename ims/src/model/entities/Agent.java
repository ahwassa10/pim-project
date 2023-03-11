package model.entities;

import java.util.List;

public interface Agent extends Entity {
	List<Attribute<?>> getAttributes();
}
