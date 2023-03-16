package information;

public final class SimpleInfoType implements InfoType {
	private final InfoTypeName infoTypeName;
	
	public SimpleInfoType(InfoTypeName infoTypeName) {
		this.infoTypeName = infoTypeName;
	}
	
	public InfoTypeName getInfoTypeName() {
		return infoTypeName;
	}
	
	public String toString() {
		return String.format("InfoType<%s>", this.getInfoTypeName());
	}
}
