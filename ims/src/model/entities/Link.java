package model.entities;

import java.util.List;

public interface Link extends Entity {
	public List<Entity> getParticipants();
}
