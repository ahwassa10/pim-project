package sys_d;

public interface DMS_Setup {
	static DMS quick_dms() {
		return new DMS("C:\\Users\\Primary\\Desktop\\import",
					   "C:\\Users\\Primary\\Desktop\\storage",
					   "C:\\Users\\Primary\\Desktop\\output",
					   "C:\\Users\\Primary\\Desktop\\export");
	}
}
