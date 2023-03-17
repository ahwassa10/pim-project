package information;

public final class SimpleInfoPair<V extends Info> implements InfoPair<V> {
	private final InfoType infoType;
	private final V value;
	
	public SimpleInfoPair(InfoType infoType, V value) {
		this.infoType = infoType;
		this.value = value;
	}
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof SimpleInfoPair<?>)) {return false;}
		
		SimpleInfoPair<?> aip = (SimpleInfoPair<?>) o;
		return value.equals(aip.value);
	}
	
	public InfoType getInfoType() {
		return infoType;
	}
	
	public V getValue() {
		return value;
	}
	
	public int hashCode() {
		return value.hashCode();
	}
	
	public String toString() {
		return String.format("<%s, %s>",
				getInfoType(),
				value);
	}
}