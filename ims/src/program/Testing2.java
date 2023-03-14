package program;

import information.identity.UUIDIdentity;
import inmemory.IMS;
import inmemory.SystemEntity;

public class Testing2 {
	public static void main(String[] args) {
		IMS system = new IMS();
		
		UUIDIdentity i = UUIDIdentity.newIdentifier();
		SystemEntity e1 = system.getEntity(i);
		
		System.out.println(e1.getIdentity());
		System.out.println(system.getEntity(i).getIdentity());
		
		system.getEntity(UUIDIdentity.newIdentifier());
		
		system.getAttributes().forEach(attribute -> System.out.println(attribute));
	}
}
