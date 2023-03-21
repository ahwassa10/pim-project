package sys_i.types;

public final class Filesize {
	private Filesize() {}

	public static boolean isValidFilesize(long test_size) {
		return test_size > 0;
	}
}
