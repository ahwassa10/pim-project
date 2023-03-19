package sys_d;

public final class SimpleDataPair implements DataPair {
	private String data;
	private String qualifier;
	
	SimpleDataPair(String data, String qualifier) {
		if (data == null) {
			throw new IllegalArgumentException("Data cannot be null");
		} else if (qualifier == null) {
			throw new IllegalArgumentException("Qualifier cannot be null");
		} else {
			this.data = data;
			this.qualifier = qualifier;
		}
	}
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof DataPair)) {return false;}
		
		DataPair dp = (DataPair) o;
		return qualifier.equals(dp.getQualifier()) && data.equals(dp.getData());
	}
	
	public String getData() {
		return data;
	}
	
	public String getQualifier() {
		return qualifier;
	}
	
	public int hashCode() {
		int result = data.hashCode();
		result = 31 * result + qualifier.hashCode();
		
		return result;
	}
	
	public String toString() {
		return String.format("DataPair<Qualifier:%s, Data:%s>", qualifier, data);
	}
}
