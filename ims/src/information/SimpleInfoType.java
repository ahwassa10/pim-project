package information;

import java.util.regex.Pattern;

public final class SimpleInfoType implements InfoType {
	// Matches a string that starts with an uppercase letter followed by
	// zero or more occurrences of any letter (both uppercase and
	// lowercase) or the hyphen -.
	private static Pattern pattern = Pattern.compile("[A-Z][a-zA-Z[-]]*");
	private static final int MAX_LENGTH = 128;
	private static final int MIN_LENGTH = 1;
	
	private final String infoTypeName;
	
	public SimpleInfoType(String infoTypeName) {
		if (!isValidInfoTypeName(infoTypeName)) {
			throw new IllegalArgumentException("Invalid infoType name");
		} else {
			this.infoTypeName = infoTypeName;
		}
	}
	
	public static boolean isValidInfoTypeName(String test_string) {
		if ((test_string == null) ||
			(test_string.length() < MIN_LENGTH) ||
			(test_string.length() > MAX_LENGTH)) {
			return false;
		}
		
		return pattern.matcher(test_string).matches();
	}
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof SimpleInfoType)) {return false;}
		
		SimpleInfoType sit = (SimpleInfoType) o;
		return infoTypeName.equals(sit.infoTypeName);
	}
	
	public String getName() {
		return infoTypeName;
	}
	
	public int hashCode() {
		return infoTypeName.hashCode();
	}
	
	public String toString() {
		return String.format("InfoType<%s>", infoTypeName);
	}
}
