package information;

public final class SimpleInfo implements Info {
	private final InfoType dataType;
	private final Data data;
	
	public SimpleInfo(InfoType dataType, Data data) {
		this.dataType = dataType;
		this.data = data;
	}
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof SimpleInfo)) {return false;}
		
		SimpleInfo sdp = (SimpleInfo) o;
		return dataType.equals(dataType) && data.equals(sdp.data);
	}
	
	public InfoType getDataType() {
		return dataType;
	}
	
	public Data getData() {
		return data;
	}
	
	public int hashCode() {
		int result = dataType.hashCode();
		result = result * 31 + data.hashCode();
		
		return result;
	}
	
	public String toString() {
		return String.format("<%s, %s>",
				dataType,
				data);
	}
}