package entity.types;

public final class Filename {
	private static final Character[] INVALID_FILENAME_CHARS =
		{'"', '*', '<', '>', '?', '|', '\000'};
	private static final int MIN_FILENAME_LENGTH = 1;
	private static final int MAX_FILENAME_LENGTH = 255;
	
	private Filename() {}
	
	public static boolean isValidFilename(String test_string) {
		if (test_string == null ||
			test_string.length() < MIN_FILENAME_LENGTH ||
			test_string.length() > MAX_FILENAME_LENGTH) {
			return false;
		}
		for (char c : INVALID_FILENAME_CHARS) {
			if (test_string.contains(String.valueOf(c))) {
				return false;
			}
		}
		return true;
	}
}
