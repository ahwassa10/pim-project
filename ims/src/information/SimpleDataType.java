package information;

import java.util.function.Predicate;

public final class SimpleDataType implements DataType {
	private final String dataTypeName;
	private final Predicate<Object> predicate; 
	
	public SimpleDataType(String dataTypeName, Predicate<Object> predicate) {
		if (predicate == null) {
			throw new IllegalArgumentException("Predicate cannot be null");
		} else if (!DataType.isValidDataTypeName(dataTypeName)) {
			throw new IllegalArgumentException("Invalid dataType name");
		} else {
			this.dataTypeName = dataTypeName;
			this.predicate = predicate;
		}
	}
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof SimpleDataType)) {return false;}
		
		SimpleDataType sdt = (SimpleDataType) o;
		return dataTypeName.equals(sdt.dataTypeName);
	}
	
	public String getDataTypeName() {
		return dataTypeName;
	}
	
	public int hashCode() {
		return dataTypeName.hashCode();
	}
	
	public String toString() {
		return String.format("DataType<%s>", dataTypeName);
	}
	
	public boolean qualifies(Object o) {
		return predicate.test(o);
	}
}
