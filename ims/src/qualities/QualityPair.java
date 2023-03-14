package qualities;

import information.InfoTypeName;

public interface QualityPair<V> {
	InfoTypeName getQualityType();
	V getValue();
}
