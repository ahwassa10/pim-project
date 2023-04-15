package program;

import java.io.IOException;

import entity.EntitySystem;
import entity.EntitySystemBuilder;
import entity.Identifier;
import source.FileSource;
import source.FileSourceBuilder;

public class Testing {
    public static void main(String[] args) throws IOException {
        EntitySystem es = EntitySystemBuilder.test_entitysystem();
        FileSource fs = FileSourceBuilder.test_rms(es);
        
        Identifier entity1 = fs.importFile();
        es.tag("anime", entity1);
        es.tag("future", entity1);
        //String entity2 = fs.importFile();
        
        es.printSS();
    }
}
