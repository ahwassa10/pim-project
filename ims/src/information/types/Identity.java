package information.types;

import java.util.UUID;

import information.AbstractSingleValueInfo;
import information.SimpleInfoType;
import sys_d.InfoType;

public final class Identity extends AbstractSingleValueInfo<UUID> {
	private static final InfoType DATA_TYPE =
			new SimpleInfoType("Identity");
	
	private Identity(UUID identity) {
		super(identity);
	}
	
	public static Identity newIdentifier() {
		return new Identity(UUID.randomUUID());
	}
	
	public InfoType getDataType() {
		return DATA_TYPE;
	}
}