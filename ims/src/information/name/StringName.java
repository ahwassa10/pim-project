package information.name;

import information.SimpleInfoPair;
import information.InfoPair;

public final class StringName implements Name {
	private static final int MAX_NAME_LENGTH = 128;
	
	private final String stringName;
	
	public StringName(String stringName) {
		if (!isValidStringName(stringName)) {
			throw new IllegalArgumentException("Cannot create a name from this string");
		} else {
			this.stringName = stringName;
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
		return new SimpleInfoPair<Name>(Name.asInfoType(), this);
	}
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof StringName)) { return false;}
		
		StringName sn = (StringName) o;
		return stringName.equals(sn.stringName);
	}
	
	public String getName() {
		return stringName;
	}
	
	public int hashCode() {
		return stringName.hashCode();
	}
	
	public String toString() {
		return stringName;
	}
}
