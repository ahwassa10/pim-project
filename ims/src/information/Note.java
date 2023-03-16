package information;

public final class Note extends AbstractSingleValueInfo<String> {
	private static final InfoType INFO_TYPE =
			new SimpleInfoType(InfoTypeName.from("Note"));
	private static final int MAX_NOTE_LENGTH = 1024;
	
	private Note(String note) {
		super(note);
	}
	
	public static Note from(String note) {
		if (!isValidStringNote(note)) {
			throw new IllegalArgumentException("Cannot create a note from this string");
		} else {
			return new Note(note);
		}
	}
	
	public static boolean isValidStringNote(String test_string) {
		if ((test_string == null) ||
			(test_string.length() <= 0) ||
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
	
	public InfoPair<Note> asInfoPair() {
		return new SimpleInfoPair<Note>(INFO_TYPE, this);
	}
	
	public InfoType getInfoType() {
		return INFO_TYPE;
	}
}
