package information;

public final class Note implements Info {
	private static final InfoType INFO_TYPE =
			new SimpleInfoType(InfoTypeName.from("Note"));
	private static final int MAX_NOTE_LENGTH = 1024;
	
	private final String note;
	
	public Note(String note) {
		if (!isValidStringNote(note)) {
			throw new IllegalArgumentException("Cannot create a note from this string");
		} else {
			this.note = note;
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
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof Note)) {return false;}
		
		Note sn = (Note) o;
		return note.equals(sn.note);
	}
	
	public InfoType getInfoType() {
		return INFO_TYPE;
	}
	
	public String getNote() {
		return note;
	}
	
	public int hashCode() {
		return note.hashCode();
	}
	
	public String toString() {
		return note;
	}
}
