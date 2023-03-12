package inmemory;

import java.util.HashMap;
import java.util.List;

import model.entities.Agent;
import model.entities.Attribute;
import model.entities.Property;
import model.qualities.Identity;

public class IMS implements Agent {
	private HashMap<Identity, SystemEntity> identities = new HashMap<>();
	
	public List<Attribute<?>> getAttributes() {
		return List.of();
	}
	
	public SystemEntity getEntity(Identity identity) {
		if (identity == null) {
			throw new IllegalArgumentException("Identity cannot be null");
		}
		
		if (identities.containsKey(identity)) {
			return identities.get(identity);
		} else {
			SystemEntity se = new SystemEntity(identity) {
				public List<Property<?>> getProperties() {
					return List.of(Property.from(identity.getQualityType(), identity));
				}
			};
			identities.put(identity, se);
			return se;
		}
	}
	
	public List<Property<?>> getProperties() {
		return List.of();
	}
}
