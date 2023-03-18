package information;

public class Filesize extends AbstractSingleValueInfo<Long> {
	private static final DataType DATA_TYPE =
			new SimpleDataType("Filesize");
	
	private Filesize(long filesize) {
		super(filesize);
	}
	
	public static Filesize from(long filesize) {
		if (filesize < 0) {
			throw new IllegalArgumentException("Negative filesize is impossible");
		} else {
			return new Filesize(filesize);
		}
	}
	
	public DataType getDataType() {
		return DATA_TYPE;
	}
	
}
