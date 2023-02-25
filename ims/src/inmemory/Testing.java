package inmemory;

import model.entities.*;
import model.properties.*;

public class Testing {
	public static void main(String[] args) {
		Entity e = new MemEntity();
		Identity i = new MemIdentity();
		
		IdentityTable t = new IdentityTable();
		t.set(i, e);
		
		System.out.println(t.read(i));
		
	}
}
