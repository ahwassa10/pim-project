package program;

import java.io.IOException;
import java.util.Set;

import entity.EntitySystem;
import entity.EntitySystemBuilder;
import entity.Identifier;
import entity.TagIdentifier;
import entity.TagSystem;
import qualitygardens.selectiontag.SelectionTagGarden;
import source.FileSource;
import source.FileSourceBuilder;

public class Testing {
    public static void main(String[] args) throws IOException {
        EntitySystem es = EntitySystemBuilder.test_entitysystem();
        FileSource fs = FileSourceBuilder.test_rms(es);
        TagSystem ts = es.getTagSystem();
        SelectionTagGarden stg = es.getSelectionTagGarden();
        
        System.out.println(ts.tagNameSet());
        
        TagIdentifier green = ts.createAndAdd("green");
        TagIdentifier future = ts.createAndAdd("future");
        System.out.println(ts.tagNameSet());
        
        for (String tag : stg.selectionTagSet()) {
            System.out.println(tag + ": " + stg.selectionTagValueSet(tag));
        }
        
        
        /*
        Identifier entity1 = fs.importFile();
        ts.associate(green, entity1);
        ts.associate(future, entity1);
        es.printSS();
        */
        //String entity2 = fs.importFile();
        
    }
}
