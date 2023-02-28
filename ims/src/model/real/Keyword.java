package model.real;

import java.util.Arrays;

public class Keyword {
	private String keyword;
	
	private static final int MAX_LENGTH = 32;
	
	private static final char[] ALLOWED_CHARACTERS =
			new char[] {'_', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
				        'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
				        'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
	
	public static boolean isValidKeyword(String test_string) {
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
	
	public Keyword(String k) {
		if (!isValidKeyword(k)) {
			throw new IllegalArgumentException("Invalid tag name");
		} else {
			this.keyword = k;
		}
	}
	
	public String getKeyword() {
		return keyword;
	}
}
