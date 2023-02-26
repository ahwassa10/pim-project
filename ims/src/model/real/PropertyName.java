package model.real;

import java.util.Arrays;

public class PropertyName {
	private String propertyName;
	
	private static final int MAX_LENGTH = 128;
	
	private static final char[] ALLOWED_CHARACTERS =
			new char[] {'-', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
					    'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
					    'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
	
	public static boolean isValidPropertyName(String test_string) {
		if (test_string == null) {
			return false;
		}
		if (test_string.length() > MAX_LENGTH) {
			return false;
		}
		for (char c : test_string.toCharArray()) {
			if (Arrays.binarySearch(ALLOWED_CHARACTERS, c) < 0) {
				return false;
			}
		}
		return true;
	}
	
	public PropertyName(String pn) {
		if (isValidPropertyName(pn)) {
			throw new IllegalArgumentException("Invalid property name");
		} else {
			this.propertyName = pn;
		}
	}
	
	public String getPropertyName() {
		return propertyName;
	}
}
