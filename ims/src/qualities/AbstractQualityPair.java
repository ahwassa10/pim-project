package qualities;

public abstract class AbstractQualityPair<V> implements QualityPair<V> {
	private V value;
	
	protected AbstractQualityPair(V value) {
		this.value = value;
	}
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof AbstractQualityPair<?>)) {return false;}
		
		AbstractQualityPair<?> aq = (AbstractQualityPair<?>) o;
		return value.equals(aq.value);
	}
	
	public abstract QualityName getQualityType();
	
	public V getValue() {
		return value;
	}
	
	public int hashCode() {
		return value.hashCode();
	}
}
