package program;

import sys_d.DMS;
import sys_d.DMSBuilder;
import sys_r.RMS;
import sys_r.RMSBuilder;

public class Testing2 {
	public static void main(String[] args) {
		DMS dms = DMSBuilder.test_dms();
		RMS rms = RMSBuilder.test_rms();
		
		System.out.println(dms);
		System.out.println(rms);
		
		rms.importFiles();
	}
}
