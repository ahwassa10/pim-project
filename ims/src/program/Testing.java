package program;

import java.util.Map;

import sys_d.DMS;
import sys_d.DMSBuilder;
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
			dms.saveData("user", "entity0",
					Map.of("Name", "Test name",
						   "Note", "This is a note",
						   "Random", "1234",
						   "Key", "Value"));
			
			dms.saveData("user", "entity1",
					Map.of("Name", "Entity One",
						   "Score", "123",
						   "Random", "4321"));
			
			System.out.println(dms.getData("user", "entity0"));
			System.out.println(dms.getData("user", "entity1"));
			System.out.println(dms.getData("user", "entity2"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
