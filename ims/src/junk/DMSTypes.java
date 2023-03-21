package junk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

final class DMSTypes implements Types {
	private final Map<String, InfoType> types = new HashMap<>();
	
	DMSTypes() {}
	
	public int countTypes() {
		return types.size();
	}
	
	public InfoType getInfoType(String typeName) {
		if (types.containsKey(typeName)) {
			return types.get(typeName);
		} else {
			throw new IllegalArgumentException("This InfoType does not exist");
		}
	}
	
	public boolean hasType(String typeName) {
		return types.containsKey(typeName);
	}
	
	public List<String> listTypeNames() {
		return List.copyOf(types.keySet());
	}
	
	public InfoType makeInfoType(Predicate<String> dataTester,
							 	 Function<String, Info> dataToInfo,
							 	 Function<Info, String> infoToData,
							 	 String typeName) {

		if (types.containsKey(typeName)) {
			throw new IllegalArgumentException("This InfoType already exists");
		} else {
			InfoType type =
					new SimpleInfoType(dataTester, dataToInfo, infoToData, typeName);
			types.put(typeName, type);
			return type;
		}
	}
}
