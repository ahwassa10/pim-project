package inmemory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import model.entities.Entity;

public class IdentityTable {
	private Map<UUID, Entity> map = new HashMap<>();
	
	public boolean contains(Identity identity) {
		return map.containsKey(identity.getIdentity());
	}
	
	public void set(Identity identity, Entity entity) {
		map.put(identity.getIdentity(), entity);
	}
	
	public Entity read(Identity identity) {
		return map.get(identity.getIdentity());
	}
	
	public void delete(Identity identity) {
		map.remove(identity.getIdentity());
	}
}
