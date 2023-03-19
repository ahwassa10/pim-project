package information;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public final class InfoTypes {
	private static final Map<String, InfoType> types = new HashMap<>();
	
	private InfoTypes() {}
	
	private static final class SimpleInfoType implements InfoType {
		private final Function<String, Info> function;
		private final Predicate<String> predicate;
		private final String typeName;
		
		public SimpleInfoType(String typeName,
					   Predicate<String> predicate,
					   Function<String, Info> function) {
			if (function == null) {
				throw new IllegalArgumentException("Function cannot be null");
			} else if (predicate == null) {
				throw new IllegalArgumentException("Predicate cannot be null");
			} else if (!InfoType.isValidTypeName(typeName)) {
				throw new IllegalArgumentException("Invalid dataType name");
			} else {
				this.function = function;
				this.predicate = predicate;
				this.typeName = typeName;
			}
		}
		
		public Info asInfo(String data) {
			return function.apply(data);
		}
		
		public boolean equals(Object o) {
			if (o == this) {return true;}
			if (!(o instanceof SimpleInfoType)) {return false;}
			
			SimpleInfoType sdt = (SimpleInfoType) o;
			return typeName.equals(sdt.typeName);
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
			return predicate.test(o);
		}
	}
	
	public static boolean exists(String typeName) {
		return types.containsKey(typeName);
	}
	
	public static InfoType from(String typeName,
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
	
	public static InfoType get(String typeName) {
		if (types.containsKey(typeName)) {
			return types.get(typeName);
		} else {
			throw new IllegalArgumentException("This InfoType does not exist");
		}
	}
}
