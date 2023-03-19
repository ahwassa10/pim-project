package information;

public final class SimpleData implements Data{
	private final Object data;
	
	public SimpleData(Object data) {
		this.data = data;
	}
	
	public Object asObject() {
		return data;
	}
	
	public String asText() {
		return data.toString();
	}
}
