package program;

import java.io.IOException;

import entity.EntitySystem;
import entity.EntitySystemBuilder;
import entity.Identifier;
import entity.TagIdentifier;
import entity.TagSystem;
import source.FileSource;
import source.FileSourceBuilder;

public class Testing {
    public static void main(String[] args) throws IOException {
        EntitySystem es = EntitySystemBuilder.test_entitysystem();
        FileSource fs = FileSourceBuilder.test_rms(es);
        TagSystem ts = es.getTagSystem();
        
        System.out.println(ts.tagNameSet());
        
        Identifier entity1 = fs.importFile();
        TagIdentifier green = ts.createAndAdd("green");
        TagIdentifier future = ts.createAndAdd("future");
        System.out.println(ts.tagNameSet());
        
        ts.associate(green, entity1);
        ts.associate(future, entity1);
        es.printSS();
        
        //String entity2 = fs.importFile();
        
    }
}
