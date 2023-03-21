package program;

import java.util.Map;

import sys_d.DMS;
import sys_d.DMSBuilder;
import sys_d.Quality;
import sys_i.FileEntity;
import sys_i.IMS;
import sys_i.IMSBuilder;
import sys_r.RMS;
import sys_r.RMSBuilder;

public class Testing {
	public static void main(String[] args) {
		DMS dms = DMSBuilder.test_dms();
		IMS ims = IMSBuilder.test_ims();
		RMS rms = RMSBuilder.test_rms();
		
		Map<String, String> data = rms.getData();
		System.out.println(data);
		FileEntity fe = ims.createFileEntity(data);
		System.out.println(fe);
		
		try {
			dms.saveQuality("Name", "entity0", "Test");
			dms.saveQuality("Note", "entity0", "This is a note");
			dms.saveQuality("Random", "entity0", "134242452");
			dms.saveQuality(Quality.from("Name", "entity1", "Another name"));
			
			System.out.println(dms.getData("entity0"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
