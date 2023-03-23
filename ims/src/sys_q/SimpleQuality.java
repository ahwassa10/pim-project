package sys_q;

final class SimpleQuality implements Quality {
	private final String agent;
	private final String key;
	private final String entity;
	private final String value;
	
	SimpleQuality(String agent, String key, String entity, String value) {
		this.agent = agent;
		this.key = key;
		this.entity = entity;
		this.value = value;
	}
	
	public String getAgent() {
		return agent;
	}
	
	public String getKey() {
		return key;
	}
	
	public String getEntity() {
		return entity;
	}
	
	public String getValue() {
		return value;
	}
	
	public String toString() {
		return String.format("Quality<Agent: %s, Key: %s, Entity: %s, Value: %s>",
				agent, key, entity, value);
	}
}
