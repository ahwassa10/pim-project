package model.entities;

import java.util.UUID;

import model.information.InformationType;
import model.real.InformationName;

public abstract class Identity implements Entity, InformationType {
	public InformationName getName() {
		return InformationName.from("identity");
	}
	
}
