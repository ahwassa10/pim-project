package program;

import inmemory.IMS;
import inmemory.SystemEntity;
import model.qualities.Identity;

public class Testing2 {
	public static void main(String[] args) {
		IMS system = new IMS();
		
		Identity i = Identity.newIdentifier();
		SystemEntity e = system.getEntity(i);
		
		System.out.println(e.getIdentity());
		
		System.out.println(system.getEntity(i).getIdentity());
	}
}
