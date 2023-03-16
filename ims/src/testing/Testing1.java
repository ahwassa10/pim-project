package testing;

import information.InfoPair;
import information.Name;
import information.Note;

public class Testing1 {
	public static void main(String[] args) {
		Name n1 = new Name("Testing names");
		InfoPair<Name> i1 = n1.asInfoPair();
		InfoPair<Name> i2 = n1.asInfoPair();
		
		// Each InfoPair is a unique instance.
		// Each call to asInfoPair returns a new instance.
		System.out.println("1) " + (i1 == i2));
		
		// Same value is inside each InfoPair
		System.out.println("2) " + (i1.getValue() == i2.getValue()));
		
		// Different InfoType instance in each InfoPair.
		System.out.println("3) " + (i1.getInfoType() == i2.getInfoType()));
		
		// Each call to getInfoType returns the same instance.
		System.out.println("4) " + (i1.getInfoType() == i1.getInfoType()));
		
		// Each call to getInfoType returns a new instance.
		System.out.println("5) " + (n1.getInfoType() == n1.getInfoType()));
		
		// SimpleInfoPair defines an equals method for all InfoPairs.
		System.out.println("6) " + (i1.equals(i2)));
		
		// SimpleInfoType defines an equals method for all InfoTypes.
		System.out.println("7) " + (i1.getInfoType().equals(i2.getInfoType())));
		
		// InfoTypeName defines an equals method for InfoTypes.
		System.out.println("8) " + (i1.getInfoTypeName().equals(i2.getInfoTypeName())));
		
		// A new instance of InfoTypeName is created every time.
		System.out.println("9) " + (i1.getInfoTypeName() == i2.getInfoTypeName()));
		
		System.out.println(i1);
		System.out.println(i1.getInfoType());
		System.out.println(i1.getValue());
		
		Note note = new Note("This is a note");
		InfoPair<Note> i3 = note.asInfoPair();
		
		System.out.println(i3.equals(i1));
		System.out.println(i3);
		System.out.println(i3.getInfoType());
		System.out.println(i3.getValue());
	}
}
