package inmemory;

import java.util.ArrayList;
import java.util.List;

import qualities.Quality;

public class User implements Agent {
	private List<Attribute<?>> attributes = new ArrayList<>();
	
	User() {}
	
	public void attribute(Entity e, Quality q) {
		if (e == null || q == null) {
			throw new IllegalArgumentException("Entity and Quality cannot be null");
		}
		
		attributes.add(Attribute.from(e, q.getQualityType(), q));
	}
	
	public List<Attribute<?>> getAttributes(Entity e) {
		if (e == null) {
			throw new IllegalArgumentException("Entity cannot be null");
		}
		
		List<Attribute<?>> entityAttributes = new ArrayList<>();
		for (Attribute<?> a : attributes) {
			if (a.getEntity().equals(e)) {
				entityAttributes.add(a);
			}
		}
		return entityAttributes;
	}
	
	public List<Attribute<?>> getAttributes() {
		return attributes;
	}
	
	public List<Property<?>> getProperties() {
		return List.of();
	}
	
}
