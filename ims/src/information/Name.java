package information;

public class Name {
	public static final int MAX_NAME_LENGTH = 128;
	public static final int MIN_NAME_LENGTH = 0;
	public static final InfoType DATA_TYPE =
			new SimpleDataType("Name", Name::isValidName);
	private static final SimpleInfoFactory<String> sif =
			new SimpleInfoFactory<>(DATA_TYPE);
	
	private Name() {}
	
	public static boolean isValidName(Object o) {
		if (!(o instanceof String)) {
			return false;
		}
		String test_string = (String) o;
		
		if ((test_string == null) ||
			(test_string.length() < MIN_NAME_LENGTH) ||
			(test_string.length() > MAX_NAME_LENGTH)) {
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
	
	public static Info from(String name) {
		return sif.from(name);
	}
	
	public static String get(Info info) {
		return sif.get(info);
	}
}
