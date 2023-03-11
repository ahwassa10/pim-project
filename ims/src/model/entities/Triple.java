package model.entities;

public class Triple<K, Q, V> {
	private final K key;
	private final Q qualifier;
	private final V value;
	
	private Triple(K key, Q qualifier, V value) {
		this.key = key;
		this.qualifier = qualifier;
		this.value = value;
	}
	
	public static <K, Q, V> Triple<K, Q, V> of (K key, Q qualifier, V value) {
		return new Triple<K, Q, V>(key, qualifier, value);
	}
	
	public Pair<K, Pair<Q, V>> asPairs() {
		return Pair.of(key, Pair.of(qualifier, value));
	}
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof Triple<?, ?, ?>)) {return false;}
		
		Triple<?, ?, ?> t = (Triple<?, ?, ?>) o;
		
		return key.equals(t.key) &&
			   qualifier.equals(t.qualifier) &&
			   value.equals(t.value);
	}
	
	public K getKey() {
		return key;
	}
	
	public V getValue() {
		return value;
	}
	
	public Q getQualifier() {
		return qualifier;
	}
	
	public int hashCode() {
		// Hashcode recipe provided in Effective Java 3ed.
		int result = key.hashCode();
		result = 31 * result + qualifier.hashCode();
		result = 31 * result + value.hashCode();
		
		return result;
	}
	
	public String toString() {
		return String.format("Triple<%s, %s, %s>",
				key, qualifier, value);
	}
	
	
}
