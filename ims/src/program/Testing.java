package program;

import java.util.Map;

import sys_i.FileEntity;
import sys_i.IMS;
import sys_i.IMSBuilder;
import sys_r.RMS;
import sys_r.RMSBuilder;

public class Testing {
	public static void main(String[] args) {
		IMS ims = IMSBuilder.test_ims();
		RMS rms = RMSBuilder.test_rms();
		
		Map<String, String> data = rms.getData();
		System.out.println(data);
		FileEntity fe = ims.createFileEntity(data);
		System.out.println(fe);
	}
}
