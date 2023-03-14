package program;

import model.entities.Property;
import model.qualities.QualityType;
import structural.Name;
import structural.Note;

public class Testing4 {
	public static void main(String[] args) {
		QualityType name1 = QualityType.from("name");
		QualityType name2 = QualityType.from("name");
		
		System.out.println(name1.equals(name2));
		
		Property<Name> p1 = Property.from(name1, Name.from("Test"));
		Property<Name> p2 = Property.from(name2, Name.from("Test"));
		Property<Name> p3 = Property.from(name1, Name.from("Test"));
		
		System.out.println(p1.equals(p2));
		System.out.println(p2.equals(p3));
		System.out.println(p1.equals(p3));
	
		Property<Note> p4 = Property.from(QualityType.from("note"),
										  Note.from("Test"));
		
		System.out.println(p4.equals(p1));
	}
}
