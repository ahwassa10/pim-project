package inmemory;

import model.properties.Identity;
import model.properties.Property;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import model.entities.Entity;
import model.entities.Link;

public class IMS {
	private IdentityTable identityTable = new IdentityTable();
	
	public Entity createEntity() {
		Identity i = new Identity() {
			private UUID uuid;
			public UUID getIdentity() {
				return this.uuid;
			}
		};
		
		Entity e = new Entity() {
			public List<Property> getProperties() {
				return List.of(i);
			}
			public Set<Link> getLinks() {
				return null;
			}
		};
		
		identityTable.set(i, e);
		return e;
	}
	
}
