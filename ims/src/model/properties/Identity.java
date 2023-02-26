package model.properties;

import java.util.UUID;

public interface Identity extends Property {
	public UUID getIdentity();
	
	public default String getPropertyName() {
		return "identity";
	}
	
	public default UUID getPropertyValue() {
		return getIdentity();
	}
}
