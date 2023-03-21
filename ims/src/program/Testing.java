package program;

import java.util.Map;
import java.util.UUID;

import sys_i.FileEntity;
import sys_i.IMS;
import sys_i.IMSBuilder;
import sys_i.types.Filesize;
import sys_i.types.Identity;
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
		
		System.out.println(Identity.isValidIdentity(null));
		System.out.println(Identity.isValidIdentity(UUID.randomUUID().toString()));
		System.out.println(Filesize.isValidFilesize(null));
		System.out.println(Filesize.isValidFilesize("0"));
		System.out.println(Filesize.isValidFilesize("134"));
	}
}
