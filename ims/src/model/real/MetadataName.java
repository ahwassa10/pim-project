package model.real;

import java.util.Arrays;

public class MetadataName {
	private String metadataName;
	
	private static final int MAX_LENGTH = 128;
	
	private static final char[] ALLOWED_CHARACTERS =
			new char[] {'-', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
					    'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
					    'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
	
	public static boolean isValidMetadataName(String test_string) {
		if ((test_string == null) ||
			(test_string.length() <= 0) ||
			(test_string.length() > MAX_LENGTH)) {
			return false;
		}
		for (char c : test_string.toCharArray()) {
			if (Arrays.binarySearch(ALLOWED_CHARACTERS, c) < 0) {
				return false;
			}
		}
		return true;
	}
	
	public MetadataName(String mn) {
		if (!isValidMetadataName(mn)) {
			throw new IllegalArgumentException("Invalid metadata name");
		} else {
			this.metadataName = mn;
		}
	}
	
	public String toString() {
		return metadataName;
	}
}
