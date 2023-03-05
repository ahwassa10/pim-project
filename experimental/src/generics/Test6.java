package generics;

import java.util.List;

// Wildcard lower bounds
public class Test6 {
	// Will print a list of objects who type is a superclass of Integer.
	public static void addNumbers(List<? super Integer> list) {
		for (int i = 1; i <= 10; i++) {
			list.add(i);
		}
	}
	
	public static void addIntegers(List<Integer> list) {
		for (int i = 1; i <= 10; i++) {
			list.add(i);
		}
	}
	
	public static void main(String[] args) {
		List<Object>  l1 = List.of(0);
		List<Number>  l2 = List.of(0);
		List<Integer> l3 = List.of(0);
		
		// Works since Object is a superclass of Integer
		addNumbers(l1);
		
		
		// Does not work since List<Object> is not related to List<Integer>
		// addIntegers(l1);
		
		
		// Works since Number is a superclass of Integer
		addNumbers(l2);
		
		
		// Does not work since List<Number> is not related to List<Integer>
		// addIntegers(l2);
		
		
		// Both work.
		addNumbers(l3);
		addIntegers(l3);
	}
}
