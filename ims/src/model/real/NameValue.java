package model.real;

public class NameValue {
	private String nameValue;
	
	private static final int MAX_LENGTH = 128;
	
	public static boolean isValidNameValue(String test_string) {
		if ((test_string == null) ||
			(test_string.length() <= 0) ||
			(test_string.length() > MAX_LENGTH)) {
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
	
	private NameValue(String nn) {
		if (!isValidNameValue(nn)) {
			throw new IllegalArgumentException("Invalid name value");
		} else {
			this.nameValue = nn;
		}
	}
	
	public static NameValue from(String nn) {
		return new NameValue(nn);
	}
	
	public String toString() {
		return nameValue;
	}
}
