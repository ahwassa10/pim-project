package information;

import java.util.regex.Pattern;

public class InfoTypeName {
	// Matches a string that starts with an uppercase letter followed by
	// zero or more occurrences of any letter (both uppercase and
	// lowercase) or the hyphen -.
	private static Pattern pattern = Pattern.compile("[A-Z][a-zA-Z[-]]*");
	private static final int MAX_LENGTH = 128;
	private static final int MIN_LENGTH = 0;
	
	private String infoTypeName;
	
	public static InfoTypeName from(String infoTypeName) {
		return new InfoTypeName(infoTypeName);
	}
	
	public static boolean isValidQualityType(String test_string) {
		if ((test_string == null) ||
			(test_string.length() < MIN_LENGTH) ||
			(test_string.length() > MAX_LENGTH)) {
			return false;
		}
		
		return pattern.matcher(test_string).matches();
	}
	
	private InfoTypeName(String in) {
		if (!isValidQualityType(in)) {
			throw new IllegalArgumentException("Invalid infotype name");
		} else {
			this.infoTypeName = in;
		}
	}
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof InfoTypeName)) {return false;}
		
		InfoTypeName itn = (InfoTypeName) o;
		return infoTypeName.equals(itn.infoTypeName);
	}
	
	public int hashCode() {
		return infoTypeName.hashCode();
	}
	
	public String toString() {
		return infoTypeName;
	}
}
