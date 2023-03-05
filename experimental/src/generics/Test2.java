package generics;

// Generic static methods.
public class Test2 {
	public static class Pair<K, V> {
		private K key;
		private V value;
		
		private Pair(K key, V value) {
			this.key = key;
			this.value = value;
		}
		public K getKey() {
			return key;
		}
		public V getValue() {
			return value;
		}
		
		// For a static generic method (in this case, a static factory method),
		// we need a list of type parameters inside angle brackets. This list
		// goes after the static keyword, and before the return type. 
		public static <K, V> Pair<K, V> createPair(K key, V value) {
			return new Pair<>(key, value);
		}
		
		// Note that the type parameter names don't matter. 
		public static <T, U> Pair<T, U> otherPair(T key, U value) {
			return new Pair<>(key, value);
		}
	}
	
	public static void main(String[] args) {
		// Invoking a static generic method through type inference. Type
		// interference figures out that the arguments to the K and V
		// parameters are String, and Integer. 
		Pair<String, Integer> property = Pair.createPair("age", 21);
		System.out.println(property.getKey() + ": " + property.getValue());
		
		// Invoking a static generic method using full syntax. Here you
		// can that we are explicity passing the type arguments to the 
		// type parameters. This also explains why we need a parameter
		// list in the method definition.
		Pair<String, String> property2 = Pair.<String, String>createPair("name", "experimental testing");
		System.out.println(property2.getKey() + ": " + property2.getValue());
	}

}
