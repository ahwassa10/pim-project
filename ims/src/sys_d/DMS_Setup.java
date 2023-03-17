package sys_d;

public interface DMS_Setup {
	static DMS test_dms() {
		return new DMS("C:\\Users\\Primary\\Desktop\\storage",
					   "C:\\Users\\Primary\\Desktop\\export");
	}
}
