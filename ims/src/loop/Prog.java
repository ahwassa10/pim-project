package loop;

import java.util.Set;

import model.Entity;
import model.IMS;
import model.Tag;

public class Prog {
	public static void main(String[] arg) {
		IMS library = new IMS();
		
		Tag tech_tag = library.createTag("tech");
		Entity e = library.createName("Java 17");
		
		library.addTagToEntity(tech_tag, e);
		
		Set<Entity> results = library.getEntitiesByTag(tech_tag);
		System.out.println(results.size());
		
		
	}
}
