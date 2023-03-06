package generics;

import java.util.ArrayList;
import java.util.List;

// Testing out subtypes with generics
public class Test8 {
	public static void main(String[] args) {
		
		Integer i = 10;
		Number n = i;
		System.out.println(n);
		System.out.println(i instanceof Number);
		
		List<Number> ln = List.of(10, 11, 12);
		System.out.println(ln.toString());
		
		List<Integer> li = List.of(1, 2, 3);
		
		List<? extends Number> ln2 = li;
		System.out.println(ln2);
		
		ArrayList<Integer> ai = new ArrayList<>(List.of(100, 101, 102));
		List<Integer> li2 = ai;
		List<? extends Number> li3 = ai;
		System.out.println(li2 + " " + li3);
		
	}
}
