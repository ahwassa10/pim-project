package sys_d;

final class SimpleQuality implements Quality {
	private final String key;
	private final String qualifier;
	private final String value;
	
	SimpleQuality(String key, String qualifier, String value) {
		this.key = key;
		this.qualifier = qualifier;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	
	public String getQualifier() {
		return qualifier;
	}
	
	public String getValue() {
		return value;
	}
	
	public String toString() {
		return String.format("Quality<Key: %s, Qualifier: %s, Value: %s>",
				key, qualifier, value);
	}
}
