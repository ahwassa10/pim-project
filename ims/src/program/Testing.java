package program;

import data.FileSource;
import data.FileSourceBuilder;
import entity.EntitySystem;
import entity.EntitySystemBuilder;
import quality.QualityStore;
import quality.QualityStoreBuilder;

public class Testing {
    public static void main(String[] args) {
        QualityStore qs = QualityStoreBuilder.test_qualitystore();
        EntitySystem es = EntitySystemBuilder.test_entitysystem();
        FileSource fs = FileSourceBuilder.test_rms(es);
        fs.printFiles();

        System.out.println(fs.importFile());

        /*
         * try { qs.saveData("user", "entity0", Map.of("Name", "Test name", "Note",
         * "This is a note", "Random", "1234", "Key", "Value"));
         * 
         * qs.saveData("user", "entity1", Map.of("Name", "Entity One", "Score", "123",
         * "Random", "4321"));
         * 
         * System.out.println(qs.getData("user", "entity0"));
         * System.out.println(qs.getData("user", "entity1"));
         * System.out.println(qs.getData("user", "entity2"));
         * 
         * } catch (Exception e) { e.printStackTrace(); }
         */
    }
}
