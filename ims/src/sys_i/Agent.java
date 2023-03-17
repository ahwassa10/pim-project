package sys_i;

import java.util.List;

public interface Agent extends Entity {
	List<Attribute<?>> getAttributes();
}
