package information.testing;

import information.DataPair;
import information.Name;
import information.Note;

public class Testing1 {
	public static void main(String[] args) {
		Name n1 = Name.from("Testing names");
		DataPair i1 = n1.asDataPair();
		DataPair i2 = n1.asDataPair();
		
		// Each DataPair is a unique instance.
		// Each call to asDataPair returns a new instance.
		System.out.println("1) " + (i1 == i2));
		
		// Same value is inside each DataPair
		System.out.println("2) " + (i1.getInfo() == i2.getInfo()));
		
		// Same DataType instance in each DataPair.
		System.out.println("3) " + (i1.getDataType() == i2.getDataType()));
		
		// Each call to getDataType on a DataPair returns the same instance.
		System.out.println("4) " + (i1.getDataType() == i1.getDataType()));
		
		// Each call to getDataType on a Name returns the same instance.
		System.out.println("5) " + (n1.getDataType() == n1.getDataType()));
		
		// SimpleDataPair defines an equals method for all DataPairs.
		System.out.println("6) " + (i1.equals(i2)));
		
		// SimpleDataType defines an equals method for all DataTypes.
		System.out.println("7) " + (i1.getDataType().equals(i2.getDataType())));
		
		// The same instance of String is used for the DataTypeName.
		System.out.println("8) " + (i1.getDataTypeName() == i2.getDataTypeName()));
		
		System.out.println(i1);
		System.out.println(i1.getDataType());
		System.out.println(i1.getInfo());
		
		Note note = Note.from("This is a note");
		DataPair i3 = note.asDataPair();
		
		System.out.println(i3.equals(i1));
		System.out.println(i3);
		System.out.println(i3.getDataType());
		System.out.println(i3.getInfo());
	}
}
