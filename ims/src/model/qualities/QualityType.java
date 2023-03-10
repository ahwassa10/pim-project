package model.qualities;

import java.util.Arrays;

public class QualityType {
	
	private static final char[] ALLOWED_CHARACTERS =
			new char[] {'-', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
					    'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
					    'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
	
	private static final int MAX_LENGTH = 128;
	
	private String qualityType;
	
	private QualityType(String in) {
		if (!isValidQualityType(in)) {
			throw new IllegalArgumentException("Invalid quality type");
		} else {
			this.qualityType = in;
		}
	}
	
	public static boolean isValidQualityType(String test_string) {
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
	
	public static QualityType from(String qualityType) {
		return new QualityType(qualityType);
	}
	
	public String toString() {
		return qualityType;
	}
}
