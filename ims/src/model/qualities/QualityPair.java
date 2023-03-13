package model.qualities;

public final class QualityPair<V> {
	private final QualityType qualityType;
	private final V value;
	
	QualityPair(QualityType qualityType, V value) {
		this.qualityType = qualityType;
		this.value = value;
	}
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof QualityPair<?>)) {return false;}
		
		QualityPair<?> qp = (QualityPair<?>) o;
		return (qualityType.equals(qp.getQualityType()) &&
				value.equals(qp.value));
	}
	
	public QualityType getQualityType() {
		return qualityType;
	}
	
	public V getValue() {
		return value;
	}
	
	public int hashCode() {
		int result = qualityType.hashCode();
		result = 31 * result + value.hashCode();
		
		return result;
	}
	
	public String toString() {
		return String.format("QualityPair<%s, %s>", qualityType, value);
	}
}
