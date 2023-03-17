package information;

public interface InfoPair<V extends Info> {
	InfoType getInfoType();
	V getValue();
	
	default String getInfoTypeName() {
		return getInfoType().getName();
	}
}
