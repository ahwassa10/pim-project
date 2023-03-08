package model.information;


import java.util.List;

import model.real.InformationName;

public class Name implements InformationType {
	public InformationName getName() {
		return InformationName.from("name");
	}
	
	public List<Piece<?>> getProperties() {
		return null;
	}
}
