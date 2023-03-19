package information;

import java.util.regex.Pattern;

public interface InfoType {
	// Matches a string that starts with an uppercase letter followed by
	// zero or more occurrences of any letter (both uppercase and
	// lowercase) or the hyphen -.
	static final Pattern INFO_TYPE_PATTERN = Pattern.compile("[A-Z][a-zA-Z[-]]*");
	static final int MAX_INFO_TYPE_LENGTH = 128;
	static final int MIN_INFO_TYPE_LENGTH = 1;	
	
	public static boolean isValidDataTypeName(String test_string) {
		if ((test_string == null) ||
			(test_string.length() < MIN_INFO_TYPE_LENGTH) ||
			(test_string.length() > MAX_INFO_TYPE_LENGTH)) {
			return false;
		}	
		return INFO_TYPE_PATTERN.matcher(test_string).matches();
	}
	
	Info asInfoWith(String data);
	String getTypeName();
	boolean qualifies(String data);
}
