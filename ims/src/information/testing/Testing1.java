package information.testing;

import information.InfoType;
import information.InfoTypes;
import information.Name;

public class Testing1 {
	public static void main(String[] args) {
		//Name.isValidName("test");
		InfoType name = Name.INFO_TYPE;
		System.out.println(name);
		
		// Instance control ensures that the same instance is returned
		// each time. 
		InfoType name2 = InfoTypes.get("Name");
		System.out.println(name.equals(name2));
		System.out.println(name == name2);
	}
}
