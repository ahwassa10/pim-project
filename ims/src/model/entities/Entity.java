package model.entities;

import java.util.List;

public interface Entity {
	List<Piece<?>> getProperties();
}
