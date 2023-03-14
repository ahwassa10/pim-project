package structural;

import java.util.Arrays;

public final class Tag {
	private static final char[] ALLOWED_KEYWORD_CHARACTERS =
			new char[] {'_', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
				        'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
				        'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
	
	private static final int MAX_KEYWORD_LENGTH = 32;
	
	private String keyword;
	
	private Tag(String keyword) {
		if (!isValidKeyword(keyword)) {
			throw new IllegalArgumentException("Invalid tag name");
		} else {
			this.keyword = keyword;
		}
	}
	
	public static boolean isValidKeyword(String test_string) {
		if ((test_string == null) ||
				(test_string.length() <= 0) ||
				(test_string.length() > MAX_KEYWORD_LENGTH)) {
				return false;
			}
		for (char c : test_string.toCharArray()) {
			if (Arrays.binarySearch(ALLOWED_KEYWORD_CHARACTERS, c) < 0) {
				return false;
				}
		}
		return true;
	}
	
	public static Tag from(String keyword) {
		return new Tag(keyword);
	}
	
	public String toString() {
		return keyword;
	}
}