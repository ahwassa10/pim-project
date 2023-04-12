package program;

import java.io.IOException;

import entity.EntitySystem;
import entity.EntitySystemBuilder;
import source.FileSource;
import source.FileSourceBuilder;

public class Testing {
    public static void main(String[] args) throws IOException {
        EntitySystem es = EntitySystemBuilder.test_entitysystem();
        FileSource fs = FileSourceBuilder.test_rms(es);
        
        //String entity1 = fs.importFile();
        //String entity2 = fs.importFile();
    }
}
