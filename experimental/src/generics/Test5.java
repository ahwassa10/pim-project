package generics;

import java.util.List;

public class Test5 {
	// Note that we can specify the type bounds for a List, even though we 
	// didn't write the list interface.
	public static <T extends Number> void listPrinter1(List<T> list) {
		System.out.println(list.toString());
	}
	
	// Can print a list of Number, Integer, etc. 
	public static void listPrinter2(List<? extends Number> list) {
		System.out.println(list.toString());
	}
	
	// Can only print List<Number>
	public static void listPrinter3(List<Number> list) {
		System.out.println(list.toString());
	}
	
	// Can only print List<Object>. 
	public static void allListPrinter1(List<Object> list) {
		System.out.println(list.toString());
	}
	
	public static <T extends Object> void allListPrinter2(List<T> list) {
		System.out.println(list.toString());
	}
	
	// Uses an unbounded wild card.
	public static void allListPrinter3(List<?> list) {
		System.out.println(list.toString());
	}
	
	public static void main(String[] args) {
		listPrinter1(List.of(1, 2, 3, 4, 5));
		
		listPrinter2(List.of(5, 4, 3, 2, 1));
		
		List<Integer> li = List.of(2, 2, 2);
		
		// Works since T (Integer) extends Number
		listPrinter1(li);
		
		// Works since we're using a wildcard which essentially
		// specifies any type that implements the Number interface.
		listPrinter2(li);
		
		// Does not work since List<Number> and List<Integer> are not
		// related to each other. 
		// listPrinter3(li);
		
		// This does not work since List<Integer> is not a subclass 
		// of List<Object>. 
		// allListPrinter1(li);
		
		List<Integer> li2 = List.of(4, 4, 4);
		
		allListPrinter2(li2);
		allListPrinter3(li2);
		
		
	}
}
