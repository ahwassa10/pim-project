package information;

import sys_d.DMS;
import sys_d.Info;
import sys_d.InfoType;
import sys_d.SimpleInfo;

public final class Name {
	public static final int MAX_NAME_LENGTH = 128;
	public static final int MIN_NAME_LENGTH = 0;
	public static InfoType INFO_TYPE;
	
	private Name() {}
	
	public static void register(DMS dms) {
		INFO_TYPE = dms.getTypes().makeInfoType(Name::isValidName,
				Name::toInfo, Name::toData, "Name");
	}
	
	private static boolean isValidName(String test_string) {
		if ((test_string == null) ||
			(test_string.length() < MIN_NAME_LENGTH) ||
			(test_string.length() > MAX_NAME_LENGTH)) {
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
			throw new IllegalArgumentException("InfoType not Name");
		} else {
			return (String) info.getObject();
		}
	}
	
	private static Info toInfo(String data) {
		if (!isValidName(data)) {
			throw new IllegalArgumentException("Data does not represent a Name");
		} else {
			return new SimpleInfo(INFO_TYPE, data);
		}
	}
}
