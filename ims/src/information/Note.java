package information;

import sys_d.AbstractInfo;
import sys_d.DMSTypes;
import sys_d.Info;
import sys_d.InfoType;

public class Note {
	public static final int MAX_NOTE_LENGTH = 1024;
	public static final int MIN_NOTE_LENGTH = 0;	
	public static final InfoType INFO_TYPE =
			DMSTypes.from("Note", Note::isValidNote, Note::asInfo);
	
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
	
	public static Info asInfo(String test_string) {
		if (!isValidNote(test_string)) {
			throw new IllegalArgumentException("Data does not represent a Note");
		} else {
			return new AbstractInfo(INFO_TYPE, test_string);
		}
	}
	
	public static String getNote(Info info) {
		if (!INFO_TYPE.equals(info.getInfoType())) {
			throw new IllegalArgumentException("InfoType not Note");
		} else {
			return (String) info.getObject();
		}
	}
}
