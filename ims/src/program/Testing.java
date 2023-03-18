package program;

import sys_d.DMS;
import sys_d.DMSBuilder;
import sys_i.IMS;
import sys_i.IMSBuilder;
import sys_r.RMS;
import sys_r.RMSBuilder;

public class Testing {
	public static void main(String[] args) {
		DMS dms = DMSBuilder.test_dms();
		RMS rms = RMSBuilder.test_rms();
		IMS ims = IMSBuilder.test_ims();
		
		System.out.println(dms);
		System.out.println(rms);
		System.out.println(ims);
		
		rms.readImportFolder();
	}
}
