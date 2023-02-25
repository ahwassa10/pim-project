package inmemory;

import java.util.HashMap;
import java.util.Map;

import model.entities.Entity;
import model.properties.Identity;

public class IdentityTable {
	private Map<Identity, Entity> map = new HashMap<>();
	
	public boolean contains(Identity identity) {
		return map.containsKey(identity);
	}
	
	public void set(Identity identity, Entity entity) {
		map.put(identity, entity);
	}
	
	public Entity read(Identity identity) {
		return map.get(identity);
	}
	
	public void delete(Identity identity) {
		map.remove(identity);
	}
}
