package model.metadata;

import model.real.Keyword;
import model.real.MetadataName;

public interface Tag extends Metadata<Keyword> {
	public default MetadataName getName() {
		return MetadataName.from("tag");
	}
	
	public Keyword getValue();
}
