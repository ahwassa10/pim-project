package inmemory;

import java.util.List;

public interface Entity {
	List<Property<?>> getProperties();
}
