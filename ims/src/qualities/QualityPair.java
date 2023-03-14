package qualities;

public interface QualityPair<V> {
	QualityName getQualityType();
	V getValue();
}
