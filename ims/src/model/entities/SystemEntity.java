package model.entities;

import java.util.List;

import model.metadata.Identity;
import model.metadata.Tag;

public interface SystemEntity extends Entity {
	public Identity getIdentity();
	
	public List<Tag> getTags();
}
