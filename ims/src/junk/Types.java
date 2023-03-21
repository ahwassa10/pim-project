package junk;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Types {
	default DataPair asDataPair(Info info) {
		return new SimpleDataPair(info.getInfoType().asData(info),
				                  info.getInfoType().getTypeName());
	}
	
	default Info asInfo(DataPair dataPair) {
		String typeName = dataPair.getQualifier();
		if (!hasType(typeName)) {
			throw new IllegalArgumentException("typeName does not exist");
		} else {
			InfoType infoType = getInfoType(typeName);
			return infoType.asInfo(dataPair.getData());
		}
	}
	
	int countTypes();
	
	InfoType getInfoType(String typeName);
	
	List<String> listTypeNames();
	
	InfoType makeInfoType(Predicate<String> dataTester,
						  Function<String, Info> dataToInfo,
						  Function<Info, String> infoToData,
						  String typeName);
	
	boolean hasType(String typeName);
}
