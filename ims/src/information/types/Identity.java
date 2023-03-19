package information.types;

import java.util.UUID;

import information.AbstractSingleValueInfo;
import information.InfoType;
import information.SimpleDataType;

public final class Identity extends AbstractSingleValueInfo<UUID> {
	private static final InfoType DATA_TYPE =
			new SimpleDataType("Identity");
	
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