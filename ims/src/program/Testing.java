package program;

import java.util.Map;

import sys_i.IMS;
import sys_i.IMSBuilder;
import sys_q.QMS;
import sys_q.QMSBuilder;
import sys_r.RMS;
import sys_r.RMSBuilder;

public class Testing {
	public static void main(String[] args) {
		QMS qms = QMSBuilder.test_qms();
		IMS ims = IMSBuilder.test_ims();
		RMS rms = RMSBuilder.test_rms();
		
		Map<String, String> data = rms.getData();
		System.out.println(data);
		ims.importFileData(data);
		
		try {
			qms.saveData("user", "entity0",
					Map.of("Name", "Test name",
						   "Note", "This is a note",
						   "Random", "1234",
						   "Key", "Value"));
			
			qms.saveData("user", "entity1",
					Map.of("Name", "Entity One",
						   "Score", "123",
						   "Random", "4321"));
			
			System.out.println(qms.getData("user", "entity0"));
			System.out.println(qms.getData("user", "entity1"));
			System.out.println(qms.getData("user", "entity2"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
