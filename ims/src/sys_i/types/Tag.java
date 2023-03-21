package sys_i.types;

import java.util.regex.Pattern;

public final class Tag {
	private static final Pattern pattern =
			Pattern.compile("[a-z][a-z[_]]*");
	private static final int MAX_TAG_LENGTH = 32;
	private static final int MIN_TAG_LENGTH = 1;
	
	private Tag() {}
	
	public static boolean isValidTag(String test_string) {
		if ((test_string == null) ||
			(test_string.length() < MIN_TAG_LENGTH) ||
			(test_string.length() > MAX_TAG_LENGTH)) {
			return false;
		}
		return pattern.matcher(test_string).matches();
	}
}