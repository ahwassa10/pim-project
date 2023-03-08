package model.information;

import model.entities.Entity;
import model.real.InformationName;

public interface Information extends Entity {
	static InformationName getName() {
		return InformationName.from("information");
	}
}
