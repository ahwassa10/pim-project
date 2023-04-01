package entity.types;

public final class Filesize {
	private Filesize() {}

	public static boolean isValidFilesize(String test_string) {
		try {
			long filesize = Long.parseLong(test_string);
			return filesize >= 0;
		} catch (Exception e) {
			return false;
		}
	}
}
