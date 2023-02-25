package model.properties;

import java.util.UUID;

public interface Identity extends Property {
	public UUID getID();
	
	public default String getPropertyName() {
		return "Identity";
	}
}
