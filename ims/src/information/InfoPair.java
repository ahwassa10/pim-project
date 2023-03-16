package information;

public interface InfoPair<V extends Info> {
	InfoType getInfoType();
	V getValue();
	
	default InfoTypeName getInfoTypeName() {
		return getInfoType().getInfoTypeName();
	}
}
