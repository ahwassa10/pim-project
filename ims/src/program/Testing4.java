package program;

import model.entities.Property;
import qualities.QualityName;
import qualities.naming.StringName;
import structural.Note;

public class Testing4 {
	public static void main(String[] args) {
		QualityName name1 = QualityName.from("name");
		QualityName name2 = QualityName.from("name");
		
		System.out.println(name1.equals(name2));
		
		Property<StringName> p1 = Property.from(name1, StringName.from("Test"));
		Property<StringName> p2 = Property.from(name2, StringName.from("Test"));
		Property<StringName> p3 = Property.from(name1, StringName.from("Test"));
		
		System.out.println(p1.equals(p2));
		System.out.println(p2.equals(p3));
		System.out.println(p1.equals(p3));
	
		Property<Note> p4 = Property.from(QualityName.from("note"),
										  Note.from("Test"));
		
		System.out.println(p4.equals(p1));
	}
}
