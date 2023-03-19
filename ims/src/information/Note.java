package information;

import sys_d.DMS;
import sys_d.Info;
import sys_d.InfoType;
import sys_d.SimpleInfo;

public final class Note {
	public static final int MAX_NOTE_LENGTH = 1024;
	public static final int MIN_NOTE_LENGTH = 0;	
	public static InfoType INFO_TYPE;
	
	private Note() {}
	
	public static void register(DMS dms) {
		INFO_TYPE = dms.getTypes().makeInfoType(Note::isValidNote,
				Note::toInfo, Note::toData, "Note");
	}
	
	private static boolean isValidNote(String test_string) {
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
	
	private static String toData(Info info) {
		if (!INFO_TYPE.equals(info.getInfoType())) {
			throw new IllegalArgumentException("InfoType not Note");
		} else {
			return (String) info.getObject();
		}
	}
	
	private static Info toInfo(String data) {
		if (!isValidNote(data)) {
			throw new IllegalArgumentException("Data does not represent a Note");
		} else {
			return new SimpleInfo(INFO_TYPE, data);
		}
	}
}
