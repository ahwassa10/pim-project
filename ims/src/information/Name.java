package information;

public final class Name {
	public static final int MAX_NAME_LENGTH = 128;
	public static final int MIN_NAME_LENGTH = 0;
	public static final InfoType INFO_TYPE =
			InfoTypes.from("Name", Name::isValidName, Name::asInfo);
	
	private Name() {}
	
	public static boolean isValidName(String test_string) {
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
	
	public static Info asInfo(String test_string) {
		if (!isValidName(test_string)) {
			throw new IllegalArgumentException("Data does not represent a Name");
		} else {
			return new SimpleInfo(INFO_TYPE, test_string);
		}
	}
	
	
	public static String getName(Info info) {
		if (!INFO_TYPE.equals(info.getInfoType())) {
			throw new IllegalArgumentException("InfoType not Name");
		} else {
			return (String) info.getObject();
		}
	}
}
