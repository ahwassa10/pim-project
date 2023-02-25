package inmemory;

import model.properties.Identity;
import model.properties.Property;

import java.util.List;
import java.util.Set;

import model.entities.Entity;
import model.entities.Link;

public class IMS {
	private IdentityTable identityTable = new IdentityTable();
	
	public Entity createEntity() {
		Entity e = new Entity() {
			public List<Property> getProperties() {
				return null;
			}
			public Set<Link> getLinks() {
				return null;
			}
		};
		
		Identity i = new MemIdentity();
		
		identityTable.set(i, e);
		return e;
	}
	
}
