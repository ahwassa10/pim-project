package inmemory;

import model.entities.Pair;
import model.qualities.Name;
import model.qualities.Note;

public class Testing {
	public static void main(String[] args) {
		Name n1 = Name.from("Hello there");
		Name n2 = Name.from("Hello there");
		
		System.out.println(n1.equals(n2));
		System.out.println(n1.getQualityType());
		System.out.println(n1);
		
		Pair<String, String> p1 = Pair.of("Hello", "there");
		Pair<String, String> p2 = Pair.of("Hello", "there");
		Pair<String, String> p3 = Pair.of("hello", "hi");
		
		System.out.println(p1);
		System.out.println(p3);
		
		System.out.println(p1.equals(p2));
		System.out.println(p2.equals(p1));
		System.out.println(p2.equals(p3));
		System.out.println(p3.equals(p1));
		
		Pair<String, Note> p4 = Pair.of("note", Note.from("A note"));
		Pair<String, Note> p5 = Pair.of("note", Note.from("A note"));
		Pair<Note, Note> p6 = Pair.of(Note.from("note"), Note.from("A note"));
		System.out.println(p4);
		System.out.println(p4.equals(p1));
		System.out.println(p4.equals(p5));
		System.out.println(p6.equals(p5));
	}
}
