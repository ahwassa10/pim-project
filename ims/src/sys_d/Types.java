package sys_d;

import java.util.function.Function;
import java.util.function.Predicate;

public interface Types {
	default Info asInfo(DataPair dataPair) {
		String typeName = dataPair.getQualifier();
		if (!hasType(typeName)) {
			throw new IllegalArgumentException("typeName does not exist");
		} else {
			InfoType infoType = getInfoType(typeName);
			return infoType.asInfo(dataPair.getData());
		}
	}
	
	InfoType getInfoType(String typeName);
	InfoType makeInfoType(String typeName,
						  Predicate<String> dataTester,
						  Function<String, Info> dataToInfo);
	boolean hasType(String typeName);
}
