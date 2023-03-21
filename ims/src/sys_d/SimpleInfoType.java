package sys_d;

import java.util.function.Function;
import java.util.function.Predicate;

import junk.Info;

class SimpleInfoType implements InfoType {
	private final Predicate<String> dataTester;
	private final Function<String, Info> dataToInfo;
	private final Function<Info, String> infoToData;
	private final String typeName;
		
	public SimpleInfoType(Predicate<String> dataTester,
					      Function<String, Info> dataToInfo,
					      Function<Info, String> infoToData,
					      String typeName) {
		if (dataTester == null) {
			throw new IllegalArgumentException("dataTester predicate cannot be null");
		} else if (dataToInfo == null) {
			throw new IllegalArgumentException("dataToInfo function cannot be null");
		} else if (infoToData == null) {
			throw new IllegalArgumentException("infoToData function cannot be null");
		} else if (!InfoType.isValidTypeName(typeName)) {
			throw new IllegalArgumentException("Invalid dataType name");
		} else {
			this.dataTester = dataTester;
			this.dataToInfo = dataToInfo;
			this.infoToData = infoToData;
			this.typeName = typeName;
		}
	}
	
	public String asData(Info info) {
		return infoToData.apply(info);
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
		return String.format("InfoType:%s", typeName);
	}
	
	public boolean qualifies(String o) {
		return dataTester.test(o);
	}
}
