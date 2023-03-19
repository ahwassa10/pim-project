package information;

public final class SimpleInfo implements Info {
	private final InfoType infoType;
	private final Object object;
	
	public SimpleInfo(InfoType infoType, Object object) {
		this.infoType = infoType;
		this.object = object;
	}
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof SimpleInfo)) {return false;}
		
		SimpleInfo sdp = (SimpleInfo) o;
		return infoType.equals(infoType) && object.equals(sdp.object);
	}
	
	public InfoType getInfoType() {
		return infoType;
	}
	
	public String asData() {
		return object.toString();
	}
	
	public Object getObject() {
		return object;
	}
	
	public int hashCode() {
		int result = infoType.hashCode();
		result = result * 31 + object.hashCode();
		
		return result;
	}
	
	public String toString() {
		return String.format("<%s, %s>",
				infoType,
				object);
	}
}