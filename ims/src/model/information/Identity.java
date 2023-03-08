package model.information;

import java.util.UUID;

import model.real.InformationName;

public interface Identity extends Information {
	static InformationName getName() {
		return InformationName.from("identity");
	}
	
	static UUID instance() {
		return UUID.randomUUID();
	}
	
}
