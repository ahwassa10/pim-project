package information;

public final class SimpleInfo implements Info {
	private final DataType dataType;
	private final Data data;
	
	public SimpleInfo(DataType dataType, Data data) {
		this.dataType = dataType;
		this.data = data;
	}
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof SimpleInfo)) {return false;}
		
		SimpleInfo sdp = (SimpleInfo) o;
		return dataType.equals(dataType) && data.equals(sdp.data);
	}
	
	public DataType getDataType() {
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