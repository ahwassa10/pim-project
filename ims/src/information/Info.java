package information;

public interface Info {
	Data getData();
	DataType getDataType();
	
	default String getDataTypeName() {
		return getDataType().getDataTypeName();
	}
}
