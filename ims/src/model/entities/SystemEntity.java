package model.entities;

import java.util.List;

import model2.Identity;
import model2.Metadata;
import model2.Tag;

public interface SystemEntity extends Entity {
	public Identity getIdentity();
	
	public List<Tag> getTags();
	
	public List<Metadata<Object>> getAttributes();
}
