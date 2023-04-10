package program;

import java.io.IOException;

import data.FileSource;
import data.FileSourceBuilder;
import entity.EntitySystem;
import entity.EntitySystemBuilder;
import quality.FileQualityStore;

public class Testing {
    public static void main(String[] args) throws IOException {
        EntitySystem es = EntitySystemBuilder.test_entitysystem();
        FileSource fs = FileSourceBuilder.test_rms(es);
        FileQualityStore qs = es.getQualityStore();
        
        String entity1 = fs.importFile();
        String entity2 = fs.importFile();
        qs.remove("filename", entity1);
        qs.remove("filesize", entity2);
        qs.printIndex();
    }
}
