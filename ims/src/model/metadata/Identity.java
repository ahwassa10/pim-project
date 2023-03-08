package model.metadata;

import java.util.UUID;

import model.real.InformationType;

public interface Identity extends Information {
	public default InformationType getName() {
		return InformationType.from("identity");
	}
	
	public UUID getValue();
}
