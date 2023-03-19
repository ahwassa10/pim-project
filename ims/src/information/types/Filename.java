package information.types;

import information.AbstractSingleValueInfo;
import information.SimpleInfoType;
import sys_d.InfoType;

public final class Filename extends AbstractSingleValueInfo<String> {
	public static final InfoType DATA_TYPE =
			new SimpleInfoType("Filename");
	private static final Character[] INVALID_FILENAME_CHARS =
		{'"', '*', '<', '>', '?', '|', '\000'};
	private static final int MIN_FILENAME_LENGTH = 1;
	private static final int MAX_FILENAME_LENGTH = 255;
	
	private Filename(String filename) {
		super(filename);
	}
	
	public static Filename from(String filename) {
		if (!isValidFilename(filename)) {
			throw new IllegalArgumentException("Cannot create a filename "
					+ "from this string");
		} else {
			return new Filename(filename);
		}
	}
	
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
	
	public InfoType getDataType() {
		return DATA_TYPE;
	}
}
