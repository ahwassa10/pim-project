package generics;

// Bounded parameter types
// There are two main features:
// 1) Restrict the types that can be used as type arguments.
// 2) Invoke methods definded in the bounds. 
public class Test3 {
	
	public interface A {
		void printA();
	}
	
	public interface B {
		void printB();
	}
	
	public static class Box<T> {
		private T t;
		
		public void set(T t) {this.t = t;}
		public T get() {return this.t;}
		
		// Note that this is a non-static generic method.
		// Basically, this syntax says that we want an object, u,
		// that implements the CharSequence interface.
		public <U extends CharSequence> void inspect(U u) {
			// "extends" could mean either "extends a class" or 
			// "implements an interface". 
			System.out.println("T: " + t.getClass().getName());
			System.out.println("U: " + u.getClass().getName());
		}
		
		// inspect2() does the same thing as inspect().   
		public void inspect2(CharSequence u) {
			System.out.println("T: " + t.getClass().getName());
			System.out.println("U: " + u.getClass().getName());
		}
		
		// A type parameter can have multiple bounds.
		// Note: you can't specify multiple class/interface types
		// for a normal argument, but you can for bounds. This 
		// makes generics more powerful than regular interface
		// polymorphism
		public <U extends A & B>void doStuff(U u) {
			u.printA();
			u.printB();
		}
	}
	
	public static void main(String[] args) {
		Box<String> stringBox = new Box<>();
		
		stringBox.set("hello");
		stringBox.inspect("Other");
		
		// Results in a compile time error.
		// stringBox.inspect(3);
		
		
		stringBox.inspect("Other other");
		
		class implementer implements A, B {
			public void printA() {System.out.println("A");}
			public void printB() {System.out.println("B");}
		}
		
		// Note that we needed to use a local class to get an instance
		// that implements both interface A and interface B. We couldn't
		// use an anonymous class since we can only specify one interface
		// to extend from. 
		stringBox.doStuff(new implementer());
		
	}
}
