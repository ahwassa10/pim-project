package information;

public class Filesize extends AbstractSingleValueInfo<Integer> {
	private static final DataType DATA_TYPE =
			new SimpleDataType("Filesize");
	
	private Filesize(int filesize) {
		super(filesize);
	}
	
	public static Filesize from(int filesize) {
		if (filesize < 0) {
			throw new IllegalArgumentException("Negative filesize is impossible");
		} else {
			return new Filesize(filesize);
		}
	}
	
	public DataPair asDataPair() {
		return new SimpleDataPair(DATA_TYPE, this);
	}
	
	public DataType getDataType() {
		return DATA_TYPE;
	}
	
}
