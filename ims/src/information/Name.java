package information;

public final class Name implements Info {
	private static final InfoType INFO_TYPE =
			new SimpleInfoType(InfoTypeName.from("Name"));
	private static final int MAX_NAME_LENGTH = 128;
	
	private final String name;
	
	public Name(String stringName) {
		if (!isValidStringName(stringName)) {
			throw new IllegalArgumentException("Cannot create a name from this string");
		} else {
			this.name = stringName;
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
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof Name)) { return false;}
		
		Name sn = (Name) o;
		return name.equals(sn.name);
	}
	
	public InfoType getInfoType() {
		return INFO_TYPE;
	}
	
	public String getName() {
		return name;
	}
	
	public int hashCode() {
		return name.hashCode();
	}
	
	public String toString() {
		return name;
	}
}
