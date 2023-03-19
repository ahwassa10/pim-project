package sys_d;

public abstract class AbstractInfo implements Info {
	private final InfoType infoType;
	private final Object object;
	
	public AbstractInfo(InfoType infoType, Object object) {
		this.infoType = infoType;
		this.object = object;
	}
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof AbstractInfo)) {return false;}
		
		AbstractInfo sdp = (AbstractInfo) o;
		return infoType.equals(infoType) && object.equals(sdp.object);
	}
	
	public InfoType getInfoType() {
		return infoType;
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