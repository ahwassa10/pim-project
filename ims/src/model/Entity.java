package model;

import java.util.Set;

public interface Entity {
	public Set<Entity> getRelationships();
}
