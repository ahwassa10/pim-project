package program;

import java.util.List;

import inmemory.IMS;
import inmemory.SystemEntity;
import inmemory.User;
import model.entities.Attribute;
import qualities.naming.StringName;
import structural.Identity;
import structural.Note;

public class Testing3 {
	public static void main(String[] args) {
		IMS system = new IMS();
		User user = system.getUser();
		
		SystemEntity se = system.getEntity(Identity.newIdentifier());
		StringName n = StringName.from("A system entity");
		Note nn = Note.from("Testing user attributions");
		
		user.attribute(se, n);
		user.attribute(se, nn);
		
		List<Attribute<?>> attributes = user.getAttributes(se);
		System.out.println(attributes);
		
		SystemEntity again = system.getEntity(se.getIdentity());
		System.out.println(user.getAttributes(again));
	}
}
