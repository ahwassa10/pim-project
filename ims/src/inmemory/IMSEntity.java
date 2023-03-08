package inmemory;

import java.util.List;
import java.util.UUID;

import model.entities.Entity;
import model.information.Piece;

public class IMSEntity implements Entity {
	private UUID identity;
	
	public List<Piece<?>> getProperties() {
		return List.of()
	}
}
