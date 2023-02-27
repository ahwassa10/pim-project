package model2;

import java.util.UUID;

import model.real.MetadataName;

public interface Identity extends Metadata<UUID>{
	public default MetadataName getName() {
		return new MetadataName("identity");
	}
	
	public UUID getValue();
}
