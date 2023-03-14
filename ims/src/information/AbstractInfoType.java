package information;

public abstract class AbstractInfoType implements InfoType {
	public String toString() {
		return String.format("InfoType<%s>", this.getInfoTypeName());
	}
}
