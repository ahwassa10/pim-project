package model.qualities;

public final class Note implements Quality {
	
	private static final int MAX_NOTE_LENGTH = 1024;
	
	private static QualityType qualityType =
			QualityType.from("note");
	
	private String noteValue;
	
	private Note(String noteValue) {
		if (!isValidNoteValue(noteValue)) {
			throw new IllegalArgumentException("Invalid note value");
		} else {
			this.noteValue = noteValue;
		}
	}
	
	public static boolean isValidNoteValue(String test_string) {
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
	
	public static Note from(String noteValue) {
		return new Note(noteValue);
	}
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof Note)) {return false;}
		
		Note n = (Note) o;
		return noteValue.equals(n.toString());
	}

	public QualityType getQualityType() {
		return qualityType;
	}
	
	public int hashCode() {
		return noteValue.hashCode();
	}
	
	public String toString() {
		return noteValue;
	}
}
