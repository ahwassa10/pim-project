package model.entities;

public final class Pair<K, V> {
	private final K key;
	private final V value;
	
	private Pair(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	public static <K, V> Pair<K, V> of(K key, V value) {
		return new Pair<K, V>(key, value);
	}
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof Pair<?, ?>)) {return false;}
		
		Pair<?, ?> p = (Pair<?, ?>) o;
		return key.equals(p.getKey()) && value.equals(p.getValue());
	}
	
	public K getKey() {
		return key;
	}
	
	public V getValue() {
		return value;
	}
	
	public int hashCode() {
		// Hashcode recipe provided in Effective Java 3ed.
		int result = key.hashCode();
		result = 31 * result + value.hashCode();
		
		return result;
	}
	
	public String toString() {
		return String.format("Pair<%s, %s>", key, value);
	}
}
