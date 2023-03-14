package program;

import information.InfoTypeName;
import information.name.StringName;
import model.entities.Property;
import structural.Note;

public class Testing4 {
	public static void main(String[] args) {
		InfoTypeName name1 = InfoTypeName.from("name");
		InfoTypeName name2 = InfoTypeName.from("name");
		
		System.out.println(name1.equals(name2));
		
		Property<StringName> p1 = Property.from(name1, StringName.from("Test"));
		Property<StringName> p2 = Property.from(name2, StringName.from("Test"));
		Property<StringName> p3 = Property.from(name1, StringName.from("Test"));
		
		System.out.println(p1.equals(p2));
		System.out.println(p2.equals(p3));
		System.out.println(p1.equals(p3));
	
		Property<Note> p4 = Property.from(InfoTypeName.from("note"),
										  Note.from("Test"));
		
		System.out.println(p4.equals(p1));
	}
}
