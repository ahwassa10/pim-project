package generics;

import java.util.Collection;
import java.util.List;

// Subtype relationships
// Summary: "Given two concrete types A and B, MyClass<A> has no relationship
// to MyClass<B>, regardless of whether or not A and B are realted."
public class Test4 {
	public static class Name {
		private String name;
		private Name(String n) {this.name = n;}
		public static Name from(String n) {return new Name(n);}
		public String toString() {return this.name;}
	}
	
	public static class Title extends Name {
		private Title(String t) {super(t);}
		public static Title from(String t) {
			if (t.length() < 10) {
				throw new IllegalArgumentException("Title too short");
			}
			return new Title(t);
		}
	}
	
	public static <T extends Name> void namePrinter(T name) {
		System.out.println(name);
	}
	
	public static void main(String[] args) {
		// This works as expected
		List<Name> names = List.of(Name.from("hi"), Name.from("hello"));
		System.out.println(names.toString());
		
		// This also works as expected.
		List<Name> names2 = List.of(Name.from("hi"), Title.from("This is a long title"));
		System.out.println(names2.toString());
		
		namePrinter(Name.from("orange"));
		namePrinter(Title.from("oranges and bananas"));
		
		List<Title> titles = List.of(Title.from("This is also a long title"));
		System.out.println(titles.toString());
		
		// This is a compile time error. List<Title> and List<Name> are not
		// related, even though Title extends Name. 
		// List<Name> names3 = titles;
		
		// This is not an error since the List interface extends the 
		// Collection interface. 
		Collection<Title> colTitles = titles;
		System.out.println(colTitles.toString());
		
	}

}
