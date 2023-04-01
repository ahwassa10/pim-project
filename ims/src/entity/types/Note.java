package entity.types;

public final class Note {
	private static final int MAX_NOTE_LENGTH = 1024;
	private static final int MIN_NOTE_LENGTH = 0;
	
	private Note() {}
	
	public static boolean isValidNote(String test_string) {
		if ((test_string == null) ||
			(test_string.length() < MIN_NOTE_LENGTH) ||
			(test_string.length() > MAX_NOTE_LENGTH)) {
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
}
