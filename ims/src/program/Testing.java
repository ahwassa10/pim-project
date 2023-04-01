package program;

import java.util.Map;

import data.FileSource;
import data.FileSourceBuilder;
import entity.EntitySystem;
import entity.EntitySystemBuilder;
import quality.QualityStore;
import quality.QualityStoreBuilder;

public class Testing {
	public static void main(String[] args) {
		QualityStore qs = QualityStoreBuilder.test_qms();
		EntitySystem es = EntitySystemBuilder.test_ims();
		FileSource fs = FileSourceBuilder.test_rms();
		
		Map<String, String> data = fs.getData();
		System.out.println(data);
		es.importFileData(data);
		
		try {
			qs.saveData("user", "entity0",
					Map.of("Name", "Test name",
						   "Note", "This is a note",
						   "Random", "1234",
						   "Key", "Value"));
			
			qs.saveData("user", "entity1",
					Map.of("Name", "Entity One",
						   "Score", "123",
						   "Random", "4321"));
			
			System.out.println(qs.getData("user", "entity0"));
			System.out.println(qs.getData("user", "entity1"));
			System.out.println(qs.getData("user", "entity2"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
