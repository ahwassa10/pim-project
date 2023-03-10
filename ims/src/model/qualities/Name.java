package model.qualities;

public class Name implements Quality {
	
	private static final int MAX_NAME_LENGTH = 128;
	
	private static QualityType qualityType =
			QualityType.from("name");
	
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
	
	public QualityType getQualityType() {
		return qualityType;
	}
	
	public String toString() {
		return nameValue;
	}
}
