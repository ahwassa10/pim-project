package information;

public abstract class AbstractInfoPair<V> implements InfoPair<V> {
	private final V value;
	
	protected AbstractInfoPair(V value) {
		this.value = value;
	}
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof AbstractInfoPair<?>)) {return false;}
		
		AbstractInfoPair<?> aip = (AbstractInfoPair<?>) o;
		return value.equals(aip.value);
	}
	
	public abstract InfoType getInfoType();
	
	public V getValue() {
		return value;
	}
	
	public int hashCode() {
		return value.hashCode();
	}
	
	public String toString() {
		return String.format("InfoPair<%s, %s>",
				this.getInfoType().getInfoTypeName(),
				value);
	}
}