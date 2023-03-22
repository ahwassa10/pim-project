package sys_d;

public interface Quality {
	static Quality from(String agent, String key, String entity, String value) {
		return new SimpleQuality(agent, key, entity, value);
	}
	
	default boolean equalTo(Quality other) {
		return getAgent().equals(other.getAgent()) &&
			   getKey().equals(other.getKey()) &&
			   getEntity().equals(other.getEntity()) &&
			   getValue().equals(other.getValue());
	}
	
	public String getAgent();
	public String getKey();
	public String getEntity();
	public String getValue();
}
