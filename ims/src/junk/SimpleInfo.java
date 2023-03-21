package junk;

public class SimpleInfo implements Info {
	private final InfoType infoType;
	private final Object object;
	
	public SimpleInfo(InfoType infoType, Object object) {
		if (infoType == null) {
			throw new IllegalArgumentException("InfoType cannot be null");
		} else if (object == null) {
			throw new IllegalArgumentException("Object cannot be null");
		} else {
			this.infoType = infoType;
			this.object = object;
		}
	}
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof SimpleInfo)) {return false;}
		
		SimpleInfo i = (SimpleInfo) o;
		return infoType.equals(i.getInfoType()) && object.equals(i.getObject());
	}
	
	public InfoType getInfoType() {
		return infoType;
	}
	
	public Object getObject() {
		return object;
	}
	
	public int hashCode() {
		int result = infoType.hashCode();
		result = 31 * result + object.hashCode();
		
		return result;
	}
	
	public String toString() {
		return String.format("Info<%s, %s>", infoType, object);
	}
}
