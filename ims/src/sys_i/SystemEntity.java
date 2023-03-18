package sys_i;

import java.util.List;

import information.Identity;
import information.Info;

public interface SystemEntity {
	Identity getIdentity();
	List<Info> getQualities();
}
