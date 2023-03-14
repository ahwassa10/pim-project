package quality;

import model.qualities.QualityType;

public interface QualityPair<V> {
	QualityType getQualityType();
	V getValue();
}
