package generics;

import java.util.List;

// The most basic generic features.
public class Test1 {
	// T is a type parameter
	public static class Capsule<T> {
		private T t;
		public void set(T t) {
			this.t = t;
		}
		public T get() {
			return this.t;
		}
	}
	
	public static void main(String[] args) {
		// Integer is a type argument
		// "Capsule<Integer> c" is a generic type invocation. The type argument
		// Integer is passed to the Capsule class itself. The invocation of a 
		// generic type is known as a parameterized type.
		Capsule<Integer> c = new Capsule<>();
		c.set(25);
		System.out.println(c.get());
		
		Capsule<List<Integer>> c2 = new Capsule<>();
		c2.set(List.of(1, 2, 3));
		System.out.println(c2.get().toString());
		
		// Capsule is the raw type of the genertic type Capsule<T>. 
		// Raw types seem to be a vestige of Java code before generics.
		// Capsule c3 = new Capsule();
		// c3.set("test");
		//System.out.println(c3.get());
		
	}

}
