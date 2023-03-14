package information;

public interface InfoPair<V> {
	InfoType getInfoType();
	V getValue();
	
	default InfoTypeName getInfoTypeName() {
		return getInfoType().getInfoTypeName();
	}
}
