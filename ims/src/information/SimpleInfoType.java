package information;

public final class SimpleInfoType implements InfoType {
	private final InfoTypeName infoTypeName;
	
	public SimpleInfoType(InfoTypeName infoTypeName) {
		this.infoTypeName = infoTypeName;
	}
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof SimpleInfoType)) {return false;}
		
		SimpleInfoType sit = (SimpleInfoType) o;
		return infoTypeName.equals(sit.infoTypeName);
	}
	
	public InfoTypeName getInfoTypeName() {
		return infoTypeName;
	}
	
	public String toString() {
		return String.format("InfoType<%s>", this.getInfoTypeName());
	}
}
