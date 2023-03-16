package testing;

import information.InfoPair;
import information.name.Name;
import information.name.StringName;

public class Testing1 {
	public static void main(String[] args) {
		Name n1 = new StringName("Testing names");
		InfoPair<Name> i1 = n1.asInfoPair();
		InfoPair<Name> i2 = n1.asInfoPair();
		
		// Each InfoPair is a unique instance.
		// Each call to asInfoPair returns a new instance.
		System.out.println(i1 == i2);
		
		// Same value is inside each InfoPair
		System.out.println(i1.getValue() == i2.getValue());
		
		// Same InfoType instance in each InfoPair.
		System.out.println(i1.getInfoType() == i2.getInfoType());
		
		// Each call to getInfoType returns the same instance.
		System.out.println(i1.getInfoType() == i1.getInfoType());
		
		// Each call to asInfoType returns a new instance.
		System.out.println(Name.asInfoType() == Name.asInfoType());
		
		// AbstractInfoPair defines an equals method for all InfoPairs.
		System.out.println(i1.equals(i2));
		
		System.out.println(i1.getValue());
	}
}
