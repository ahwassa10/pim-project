package model.entities;

import java.util.List;

public interface Entity {
	List<Property<?>> getProperties();
}
