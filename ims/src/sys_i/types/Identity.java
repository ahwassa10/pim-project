package sys_i.types;

import java.util.regex.Pattern;

public final class Identity {
	// This regular expression matches UUIDs.
	// The ^ is the start of line anchor.
	// The $ is the end of line anchor.
	private static final Pattern pattern = 
			Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
	private Identity() {}
	
	public static boolean isValidIdentity(String test_string) {
		if (test_string == null) {
			return false;
		}
		return pattern.matcher(test_string).matches();
	}
}