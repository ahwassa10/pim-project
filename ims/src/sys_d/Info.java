package sys_d;

public interface Info {
	String asData();
	InfoType getInfoType();
	Object getObject();
	
	default DataPair asDataPair() {
		return new DataPair() {
			public String getData() {
				return asData();
			}
			public String getQualifier() {
				return getInfoType().getTypeName();
			}
		};
	}
}
