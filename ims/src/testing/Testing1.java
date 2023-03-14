package testing;

import information.AbstractInfoPair;
import information.name.Name;
import information.note.Note;

public class Testing1 {
	public static void main(String[] args) {
		Name n = Name.from("hello");
		AbstractInfoPair<?> n2 = n;
		
		System.out.println(n);
		System.out.println(n2);
		
		System.out.println(n.getInfoType());
		System.out.println(n.getInfoType().getInfoTypeName());
		
		Name n3 = Name.from("hello");
		System.out.println(n.equals(n3));
		
		Note nt1 = Note.from("hello");
		System.out.println(nt1);
		System.out.println(nt1.getInfoType());
		System.out.println(nt1.getInfoTypeName());
		System.out.println(nt1.equals(n));
		
	}
}
