package model.qualities;

import structural.Pair;

public final class QualityPair<V> {
	private final Pair<QualityType, V> pair;
	
	QualityPair(QualityType qualityType, V value) {
		pair = Pair.of(qualityType, value);
	}
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof QualityPair<?>)) {return false;}
		
		QualityPair<?> qp = (QualityPair<?>) o;
		return pair.equals(qp.pair);
	}
	
	public QualityType getQualityType() {
		return pair.getKey();
	}
	
	public V getValue() {
		return pair.getValue();
	}
	
	public int hashCode() {
		return pair.hashCode();
	}
	
	public String toString() {
		return String.format("QualityPair<%s, %s>",
				pair.getKey(), pair.getValue());
	}
}