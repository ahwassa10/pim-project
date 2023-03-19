package sys_d;

import java.util.function.Function;
import java.util.function.Predicate;

class SimpleInfoType implements InfoType {
	private final Function<String, Info> dataToInfo;
	private final Predicate<String> dataTester;
	private final String typeName;
		
	public SimpleInfoType(String typeName,
					   Predicate<String> dataTester,
					   Function<String, Info> dataToInfo) {
		if (dataToInfo == null) {
			throw new IllegalArgumentException("Function cannot be null");
		} else if (dataTester == null) {
			throw new IllegalArgumentException("Predicate cannot be null");
		} else if (!InfoType.isValidTypeName(typeName)) {
			throw new IllegalArgumentException("Invalid dataType name");
		} else {
			this.dataToInfo = dataToInfo;
			this.dataTester = dataTester;
			this.typeName = typeName;
		}
	}
	
	public Info asInfo(String data) {
		return dataToInfo.apply(data);
	}
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof SimpleInfoType)) {return false;}
		
		SimpleInfoType sit = (SimpleInfoType) o;
		return typeName.equals(sit.typeName);
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public int hashCode() {
		return typeName.hashCode();
	}
	
	public String toString() {
		return String.format("InfoType<%s>", typeName);
	}
	
	public boolean qualifies(String o) {
		return dataTester.test(o);
	}
}
