package junk;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Types {
	int countTypes();
	
	InfoType getInfoType(String typeName);
	
	List<String> listTypeNames();
	
	InfoType makeInfoType(Predicate<String> dataTester,
						  Function<String, Info> dataToInfo,
						  Function<Info, String> infoToData,
						  String typeName);
	
	boolean hasType(String typeName);
}
