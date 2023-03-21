package sys_d;

public interface Quality {
	static Quality from(String key, String qualifier, String value) {
		return new SimpleQuality(key, qualifier, value);
	}
	
	default boolean equalTo(Quality other) {
		return getKey().equals(other.getKey()) &&
			   getQualifier().equals(other.getQualifier()) &&
			   getValue().equals(other.getValue());
	}
	
	public String getKey();
	public String getQualifier();
	public String getValue();
}
