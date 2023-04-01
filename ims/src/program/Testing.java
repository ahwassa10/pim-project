package program;

import java.util.Map;

import DataSources.FileSource;
import DataSources.FileSourceBuilder;
import sys_i.IMS;
import sys_i.IMSBuilder;
import sys_q.QualityStore;
import sys_q.QualityStoreBuilder;

public class Testing {
	public static void main(String[] args) {
		QualityStore qms = QualityStoreBuilder.test_qms();
		IMS ims = IMSBuilder.test_ims();
		FileSource fs = FileSourceBuilder.test_rms();
		
		Map<String, String> data = fs.getData();
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
