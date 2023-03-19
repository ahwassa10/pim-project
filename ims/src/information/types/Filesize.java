package information.types;

import information.AbstractSingleValueInfo;
import information.SimpleInfoType;
import sys_d.InfoType;

public class Filesize extends AbstractSingleValueInfo<Long> {
	public static final InfoType DATA_TYPE =
			new SimpleInfoType("Filesize");
	
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
	
	public InfoType getDataType() {
		return DATA_TYPE;
	}
	
}
