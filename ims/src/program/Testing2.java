package program;

import sys_d.DMS;
import sys_d.DMS_Setup;
import sys_r.RMS;
import sys_r.RMS_Setup;

public class Testing2 {
	public static void main(String[] args) {
		DMS dms = DMS_Setup.test_dms();
		RMS rms = RMS_Setup.test_rms();
		rms.importFiles();
	}
}
