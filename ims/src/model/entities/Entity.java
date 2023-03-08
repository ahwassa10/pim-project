package model.entities;

import java.util.List;

import model.information.Piece;

public interface Entity {
	List<Piece<?>> getProperties();
}
