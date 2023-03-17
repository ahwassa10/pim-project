package sys_r;

public interface RMS_Setup {
	static RMS test_rms() {
		return new RMS("C:\\Users\\Primary\\Desktop\\import",
					   "C:\\Users\\Primary\\Desktop\\output");
	}
}
