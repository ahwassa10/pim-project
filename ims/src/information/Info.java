package information;

public interface Info {
	default String getInfoTypeName() {
		return getInfoType().getTypeName();
	}
	
	String asData();
	InfoType getInfoType();
	Object getObject();
}
