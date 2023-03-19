package information;

public class Note {
	public static final int MAX_NOTE_LENGTH = 1024;
	public static final int MIN_NOTE_LENGTH = 0;	
	public static final DataType DATA_TYPE =
			new SimpleDataType("Note", Name::isValidName);
	private static final SimpleInfoFactory<String> sif =
			new SimpleInfoFactory<>(DATA_TYPE);
	
	private Note() {}
	
	public static boolean isValidNote(Object o) {
		if (!(o instanceof String)) {
			return false;
		}
		String test_string = (String) o;
		
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
	
	public static Info from(String name) {
		return sif.from(name);
	}
	
	public static String get(Info info) {
		return sif.get(info);
	}
}
