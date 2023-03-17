package information;

public interface DataPair {
	DataType getDataType();
	Info getInfo();
	
	default String getDataTypeName() {
		return getDataType().getName();
	}
}
