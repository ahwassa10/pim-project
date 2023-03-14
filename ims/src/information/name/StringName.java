package information.name;

public final class StringName{
	private static final int MAX_NAME_LENGTH = 128;
	
	private String name;
	
	private StringName(String name) {
		if (!isValidNameValue(name)) {
			throw new IllegalArgumentException("Cannot create Name from this string");
		} else {
			this.name = name;
		}
	}
	
	public static boolean isValidNameValue(String test_string) {
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
	
	public static StringName from(String name) {
		return new StringName(name);
	}
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof StringName)) { return false;}
		
		StringName sn = (StringName) o;
		return name.equals(sn.name);
	}
	
	public int hashCode() {
		return name.hashCode();
	}
	
	public String toString() {
		return name;
	}
}
