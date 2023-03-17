package information;

import java.util.regex.Pattern;

public final class Tag extends AbstractSingleValueInfo<String> {
	private static final DataType DATA_TYPE =
			new SimpleDataType("Tag");
	private static final Pattern pattern =
			Pattern.compile("[a-z][a-z[_]]*");
	private static final int MAX_TAG_LENGTH = 32;
	private static final int MIN_TAG_LENGTH = 1;
	
	private Tag(String tag) {
		super(tag);
	}
	
	public static Tag from(String tag) {
		if (!isValidTag(tag)) {
			throw new IllegalArgumentException("Invalid tag name");
		} else {
			return new Tag(tag);
		}
	}
	
	public static boolean isValidTag(String test_string) {
		if ((test_string == null) ||
			(test_string.length() < MIN_TAG_LENGTH) ||
			(test_string.length() > MAX_TAG_LENGTH)) {
			return false;
		}
		return pattern.matcher(test_string).matches();
	}
	
	public DataPair asDataPair() {
		return new SimpleDataPair(DATA_TYPE, this);
	}
	
	public DataType getDataType() {
		return DATA_TYPE;
	}
}