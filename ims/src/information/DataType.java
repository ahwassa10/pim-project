package information;

import java.util.regex.Pattern;

public interface DataType {
	// Matches a string that starts with an uppercase letter followed by
	// zero or more occurrences of any letter (both uppercase and
	// lowercase) or the hyphen -.
	static final Pattern DATATYPE_PATTERN = Pattern.compile("[A-Z][a-zA-Z[-]]*");
	static final int MAX_DATA_YPE_LENGTH = 128;
	static final int MIN_DATATYPE_LENGTH = 1;	
	
	public static boolean isValidDataTypeName(String test_string) {
		if ((test_string == null) ||
			(test_string.length() < MIN_DATATYPE_LENGTH) ||
			(test_string.length() > MAX_DATA_YPE_LENGTH)) {
			return false;
		}	
		return DATATYPE_PATTERN.matcher(test_string).matches();
	}
	
	String getDataTypeName();
	boolean qualifies(Object o);
}
