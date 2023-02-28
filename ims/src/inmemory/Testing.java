package inmemory;

import model.entities.*;

public class Testing {
	public static void main(String[] args) {
		IMS ims = new IMS();
		SystemEntity test = ims.createEntity();
		
		System.out.println(test.getIdentity().getName());
		System.out.println(test.getIdentity().getValue());
	}
}
