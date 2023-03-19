package information;

public class SimpleInfoFactory<T> {
	private final DataType dataType;
	
	public SimpleInfoFactory(DataType dataType) {
		this.dataType = dataType;
	}
	
	public Info from(T data) {
		if (!dataType.qualifies(data)) {
			String err = String.format("%s does not qualify this data",
					dataType.getDataTypeName());
			throw new IllegalArgumentException(err);
		} else {
			return new SimpleInfo(dataType, new SimpleData(data));
		}
	}
	
	public DataType getDataType() {
		return dataType;
	}
	
	@SuppressWarnings("unchecked")
	public T get(Info info) {
		if (!dataType.equals(info.getDataType())) {
			String err = String.format("DataType of info is not %s",
					dataType.getDataTypeName());
			throw new IllegalArgumentException(err);
		} else {
			return (T) info.getData().asObject();
		}
	}
}
