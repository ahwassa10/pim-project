package program;

import java.util.List;

import sys_d.DMS;
import sys_d.DMSBuilder;
import sys_d.Info;
import sys_i.IMS;
import sys_i.IMSBuilder;
import sys_i.SystemEntity;
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
		
		List<Info> info = rms.retrieveInfo();
		System.out.println(info);
		
		SystemEntity se = ims.importInfo(info);
		System.out.println(se.getIdentity());
		System.out.println(se.getQualities());
	}
}
