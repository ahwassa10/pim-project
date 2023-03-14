package information;

import java.util.Arrays;

public class InfoTypeName {
	
	private static final char[] ALLOWED_CHARACTERS =
			new char[] {'-', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
					    'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
					    'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
	
	private static final int MAX_LENGTH = 128;
	
	private String infoTypeName;
	
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
	
	public static boolean isValidQualityType(String test_string) {
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
	
	public static InfoTypeName from(String infoTypeName) {
		return new InfoTypeName(infoTypeName);
	}
	
	public String toString() {
		return infoTypeName;
	}
}
