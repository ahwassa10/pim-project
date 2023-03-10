package model.information;


import java.util.List;

import model.real.QualityName;

public class Name implements InformationType {
	public QualityName getName() {
		return QualityName.from("name");
	}
	
	public List<Piece<?>> getProperties() {
		return null;
	}
}
