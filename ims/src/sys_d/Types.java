package sys_d;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Types {
	default Info asInfo(DataPair dataPair) {
		String typeName = dataPair.getQualifier();
		if (!hasType(typeName)) {
			throw new IllegalArgumentException("typeName does not exist");
		} else {
			InfoType infoType = getInfoType(typeName);
			return infoType.fromData(dataPair.getData());
		}
	}
	
	int countTypes();
	
	InfoType getInfoType(String typeName);
	
	List<String> listTypeNames();
	
	InfoType makeInfoType(String typeName,
						  Predicate<String> dataTester,
						  Function<String, Info> dataToInfo);
	
	boolean hasType(String typeName);
}
