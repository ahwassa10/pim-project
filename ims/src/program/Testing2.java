package program;

import inmemory.IMS;
import inmemory.SystemEntity;
import model.qualities.Identity;

public class Testing2 {
	public static void main(String[] args) {
		IMS system = new IMS();
		
		Identity i = Identity.newIdentifier();
		SystemEntity e1 = system.getEntity(i);
		
		System.out.println(e1.getIdentity());
		System.out.println(system.getEntity(i).getIdentity());
		
		system.getEntity(Identity.newIdentifier());
		
		system.getAttributes().forEach(attribute -> System.out.println(attribute));
	}
}
