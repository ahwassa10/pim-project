package model.metadata;

import java.util.UUID;

import model.real.MetadataName;

public interface Identity extends Metadata<UUID>{
	public default MetadataName getName() {
		return MetadataName.from("identity");
	}
	
	public UUID getValue();
}
