package information;

import java.util.regex.Pattern;

public final class SimpleDataType implements DataType {
	// Matches a string that starts with an uppercase letter followed by
	// zero or more occurrences of any letter (both uppercase and
	// lowercase) or the hyphen -.
	private static final Pattern pattern = Pattern.compile("[A-Z][a-zA-Z[-]]*");
	private static final int MAX_LENGTH = 128;
	private static final int MIN_LENGTH = 1;
	
	private final String dataTypeName;
	
	public SimpleDataType(String dataTypeName) {
		if (!isValidInfoTypeName(dataTypeName)) {
			throw new IllegalArgumentException("Invalid dataType name");
		} else {
			this.dataTypeName = dataTypeName;
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
		if (!(o instanceof SimpleDataType)) {return false;}
		
		SimpleDataType sdt = (SimpleDataType) o;
		return dataTypeName.equals(sdt.dataTypeName);
	}
	
	public String getName() {
		return dataTypeName;
	}
	
	public int hashCode() {
		return dataTypeName.hashCode();
	}
	
	public String toString() {
		return String.format("DataType<%s>", dataTypeName);
	}
}
