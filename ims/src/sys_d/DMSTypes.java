package sys_d;

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
	
	public InfoType makeInfoType(String typeName,
							 Predicate<String> predicate,
							 Function<String, Info> function) {

		if (types.containsKey(typeName)) {
			throw new IllegalArgumentException("This InfoType already exists");
		} else {
			InfoType type = new SimpleInfoType(typeName, predicate, function);
			types.put(typeName, type);
			return type;
		}
	}
}
