package inmemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import information.Identity;

public class IMS implements Agent {
	private HashMap<Identity, SystemEntity> identities = new HashMap<>();
	
	private User user = new User();
	
	public void deleteEntity(Identity identity) {
		if (identity == null) {
			throw new IllegalArgumentException("Identity cannot be null");
		}
		identities.remove(identity);
	}
	
	public List<Attribute<?>> getAttributes() {
		List<Attribute<?>> l = new ArrayList<>();
		
		for (Entry<Identity, SystemEntity> e : identities.entrySet()) {
			l.add(Attribute.from(e.getValue(), e.getKey().getQualityType(), e.getKey()));
		}
		return l;
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
	
	public User getUser() {
		return user;
	}
	
	public List<Property<?>> getProperties() {
		return List.of();
	}
}
