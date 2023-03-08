package model.real;

public class NoteValue {
	private String noteValue;
	
	private static final int MAX_LENGTH = 1024;
	
	public static boolean isValidNoteValue(String test_string) {
		if ((test_string == null) ||
			(test_string.length() <= 0) ||
			(test_string.length() > MAX_LENGTH)) {
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
	
	private NoteValue(String nv) {
		if (!isValidNoteValue(nv)) {
			throw new IllegalArgumentException("Invalid note value");
		} else {
			this.noteValue = nv;
		}
	}
	
	public static NoteValue from(String nv) {
		return new NoteValue(nv);
	}
	
	public String toString() {
		return noteValue;
	}
}
