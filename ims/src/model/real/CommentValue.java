package model.real;

public class CommentValue {
	private String commentValue;
	
	private static final int MAX_LENGTH = 1024;
	
	public static boolean isValidCommentValue(String test_string) {
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
	
	public CommentValue(String cv) {
		if (isValidCommentValue(cv)) {
			throw new IllegalArgumentException("Invalid comment value");
		} else {
			this.commentValue = cv;
		}
	}
	
	public String getValue() {
		return commentValue;
	}
}
