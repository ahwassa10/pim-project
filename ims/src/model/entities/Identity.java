package model.entities;

import java.util.UUID;

import model.qualities.InformationType;
import model.qualities.QualityType;

public abstract class Identity implements Entity, InformationType {
	public QualityType getName() {
		return QualityType.from("identity");
	}
	
}
