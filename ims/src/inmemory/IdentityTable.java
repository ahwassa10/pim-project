package inmemory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import model.entities.Entity;
import model.metadata.Identity;

public class IdentityTable {
	private Map<UUID, Entity> map = new HashMap<>();
	
	public boolean contains(Identity identity) {
		return map.containsKey(identity.getValue());
	}
	
	public void set(Identity identity, Entity entity) {
		map.put(identity.getValue(), entity);
	}
	
	public Entity read(Identity identity) {
		return map.get(identity.getValue());
	}
	
	public void delete(Identity identity) {
		map.remove(identity.getValue());
	}
}
