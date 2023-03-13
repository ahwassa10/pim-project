package structural;

public final class Name{
	private static final int MAX_NAME_LENGTH = 128;
	
	private String nameValue;
	
	private Name(String nameValue) {
		if (!isValidNameValue(nameValue)) {
			throw new IllegalArgumentException("Invalid name value");
		} else {
			this.nameValue = nameValue;
		}
	}
	
	public static boolean isValidNameValue(String test_string) {
		if ((test_string == null) ||
			(test_string.length() <= 0) ||
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
	
	public static Name from(String nameValue) {
		return new Name(nameValue);
	}
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof Name)) { return false;}
		
		Name n = (Name) o;
		return nameValue.equals(n.toString());
	}
	
	public int hashCode() {
		return nameValue.hashCode();
	}
	
	public String toString() {
		return nameValue;
	}
}
