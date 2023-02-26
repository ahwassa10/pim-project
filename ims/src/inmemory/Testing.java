package inmemory;

import model.entities.*;
import model.properties.*;

public class Testing {
	public static void main(String[] args) {
		IMS ims = new IMS();
		Entity test = ims.createEntity();
		
		System.out.println(test.getProperties().get(0).getPropertyName());
		System.out.println(test.getProperties().get(0).getPropertyValue());
	}
}
