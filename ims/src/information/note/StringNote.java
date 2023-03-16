package information.note;

import information.SimpleInfoPair;
import information.InfoPair;

public final class StringNote implements Note {
	private static final int MAX_NOTE_LENGTH = 1024;
	
	private final String stringNote;
	
	public StringNote(String stringNote) {
		if (!isValidStringNote(stringNote)) {
			throw new IllegalArgumentException("Cannot create a note from this string");
		} else {
			this.stringNote = stringNote;
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
		return new SimpleInfoPair<Note>(Note.asInfoType(), this);
	}
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof StringNote)) {return false;}
		
		StringNote sn = (StringNote) o;
		return stringNote.equals(sn.stringNote);
	}
	
	public String getNote() {
		return stringNote;
	}
	
	public int hashCode() {
		return stringNote.hashCode();
	}
	
	public String toString() {
		return stringNote;
	}
}
