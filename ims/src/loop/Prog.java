package loop;

import java.util.Set;

import model.MEntity;
import model.IMS;
import model.MTag;

public class Prog {
	public static void main(String[] arg) {
		IMS library = new IMS();
		
		MTag tech_tag = library.createTag("tech");
		MEntity e = library.createName("Java 17");
		
		library.addTagToEntity(tech_tag, e);
		
		Set<MEntity> results = library.getEntitiesByTag(tech_tag);
		System.out.println(results.size());
		
		
	}
}
