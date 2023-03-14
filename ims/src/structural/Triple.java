package structural;

public interface Triple<K, Q, V> {
	public K getKey();
	public Q getQualifier();
	public V getValue();
}