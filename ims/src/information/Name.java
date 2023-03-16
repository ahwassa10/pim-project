package information;

public final class Name extends AbstractSingleValueInfo<String> {
	private static final InfoType INFO_TYPE =
			new SimpleInfoType(InfoTypeName.from("Name"));
	private static final int MAX_NAME_LENGTH = 128;
	
	private Name(String name) {
		super(name);
	}
	
	public static Name from(String name) {
		if (!isValidStringName(name)) {
			throw new IllegalArgumentException("Cannot create a name from this string");
		} else {
			return new Name(name);
		}
	}
	
	public static boolean isValidStringName(String test_string) {
		if ((test_string == null) ||
			(test_string.length() < 0) ||
			(test_string.length() > MAX_NAME_LENGTH)) {
			return false;
		}
		for (char c : test_string.toCharArray()) {
			// Checks if c is a printable ascii character.
			if (c < 32 || c >= 127) {
				return false;
			}
		}
		return true;
	}
	
	public InfoPair<Name> asInfoPair() {
		return new SimpleInfoPair<Name>(INFO_TYPE, this);
	}
	
	public InfoType getInfoType() {
		return INFO_TYPE;
	}
}
