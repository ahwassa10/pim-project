package information;

public final class SimpleDataPair implements DataPair {
	private final DataType infoType;
	private final Info info;
	
	public SimpleDataPair(DataType infoType, Info info) {
		this.infoType = infoType;
		this.info = info;
	}
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof SimpleDataPair)) {return false;}
		
		SimpleDataPair sdp = (SimpleDataPair) o;
		return infoType.equals(infoType) && info.equals(sdp.info);
	}
	
	public DataType getDataType() {
		return infoType;
	}
	
	public Info getInfo() {
		return info;
	}
	
	public int hashCode() {
		int result = infoType.hashCode();
		result = result * 31 + info.hashCode();
		
		return result;
	}
	
	public String toString() {
		return String.format("<%s, %s>",
				infoType,
				info);
	}
}