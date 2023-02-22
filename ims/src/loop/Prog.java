package loop;

import java.util.Set;

import ims.IMS;
import ims.MTag;
import ims.inmemory.MemEntity;

public class Prog {
	public static void main(String[] arg) {
		IMS library = new IMS();
		
		MTag tech_tag = library.createTag("tech");
		MemEntity e = library.createName("Java 17");
		
		library.addTagToEntity(tech_tag, e);
		
		Set<MemEntity> results = library.getEntitiesByTag(tech_tag);
		System.out.println(results.size());
		
		
	}
}
