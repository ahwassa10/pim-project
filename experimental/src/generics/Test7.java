package generics;

import java.util.ArrayList;
import java.util.List;

// Using wildcards
public class Test7 {
	public static void main(String[] args) {
		// We created a list of numbers. This can include Integers and 
		// floating points.
		List<? extends Number> l = List.of(1, 2.4, 3, 4.89);
		
		// This works because anything that extends Number also extends Object.
		List<? extends Object> l2 = l;
		System.out.println(l2);
		
		// This does not work because classes might extend Number, but 
		// not be superclasses of Integer.
		// List<? super Integer> l3 = l;
		
		List<Number> l3 = List.of(10, 1.1, 5);
		List<? extends Number> l4 = l3;
		System.out.println(l4);
		
		// This works because Integer extends Number.
		List<Integer> l5 = List.of(38, 39, 40);
		List<? extends Number> l6 = l5;
		System.out.println(l6);
		// l6 can hold a List<Number>, List<Integer>, or List<Double>. 
		
		// Note: The common parent of List<Integer> and List<Number> is List<?>.
		
		List<? extends Number> l7 = new ArrayList<>();
		// l7.add(Integer.valueOf(25));
		// Results in a compile time error.
		l7.clear();
	}

}
