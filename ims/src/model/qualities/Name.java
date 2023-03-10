package model.qualities;


import java.util.List;

public class Name implements InformationType {
	public QualityType getName() {
		return QualityType.from("name");
	}
	
	public List<Piece<?>> getProperties() {
		return null;
	}
}
