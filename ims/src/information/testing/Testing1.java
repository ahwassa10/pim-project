package information.testing;

import information.Data;
import information.Info;
import information.Name;
import information.Note;

public class Testing1 {
	public static void main(String[] args) {
		Info i1 = Name.from("Testing names");
		Info i2 = Note.from("This is a note");
		Info i3 = Name.from("Testing names");
		
		// Clearly these will be different instances
		System.out.println("1) " + (i1 == i3));
		
		// One datatype instance is shared
		System.out.println("2) " + (i1.getDataType() == i3.getDataType()));
		
		// Both of these should print out "Testing names"
		System.out.println("3) " + i1.getData().asText());
		System.out.println("4) " + Name.get(i1));
	}
}
