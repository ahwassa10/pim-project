package program;

import sys_d.DMS;
import sys_d.DMS_Setup;

public class Testing2 {
	public static void main(String[] args) {
		DMS dms = DMS_Setup.quick_dms();
		dms.importFiles();
	}
}
