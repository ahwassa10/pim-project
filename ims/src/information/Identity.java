package information;

import java.util.UUID;

public final class Identity extends AbstractSingleValueInfo<UUID> {
	private static final DataType DATA_TYPE =
			new SimpleDataType("Identity");
	
	private Identity(UUID identity) {
		super(identity);
	}
	
	public static Identity newIdentifier() {
		return new Identity(UUID.randomUUID());
	}
	
	public DataType getDataType() {
		return DATA_TYPE;
	}
}