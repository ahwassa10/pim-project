package model.entities;

import java.util.List;

import model.qualities.Piece;

public interface Entity {
	List<Piece<?>> getProperties();
}
