package program;

import java.io.IOException;

import data.FileSource;
import data.FileSourceBuilder;
import entity.EntitySystem;
import entity.EntitySystemBuilder;
import quality.QualityStore;
import quality.QualityStoreBuilder;

public class Testing {
    public static void main(String[] args) throws IOException {
        EntitySystem es = EntitySystemBuilder.test_entitysystem();
        FileSource fs = FileSourceBuilder.test_rms(es);
        QualityStore qs = es.getQualityStore();
        
        qs.deleteAll();
        String entity1 = fs.importFile();
        String entity2 = fs.importFile();
        qs.printIndex();
        
        try {
            System.out.println(qs.delete("FileSystem", "Filesize", entity1));
            System.out.println(qs.delete("FileSystem", "Filesize", entity1));
            System.out.println(qs.delete("FileSystem", "Filesize", entity1));
            System.out.println(qs.delete("Filesystem", "Filesize", entity1));
            System.out.println(qs.delete("FileSystem", "Filename", entity1));
            System.out.println(qs.delete("FileSystem", "Filesize", "12342"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        qs.printIndex();
    }
}
