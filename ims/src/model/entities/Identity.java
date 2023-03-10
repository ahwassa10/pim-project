package model.entities;

import java.util.UUID;

import model.information.InformationType;
import model.real.QualityName;

public abstract class Identity implements Entity, InformationType {
	public QualityName getName() {
		return QualityName.from("identity");
	}
	
}
