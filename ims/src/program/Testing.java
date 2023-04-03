package program;

import java.io.IOException;

import data.FileSource;
import data.FileSourceBuilder;
import entity.EntitySystem;
import entity.EntitySystemBuilder;
import quality.QualityStore;
import quality.QualityStoreBuilder;

public class Testing {
    public static void main(String[] args) {
        EntitySystem es = EntitySystemBuilder.test_entitysystem();
        QualityStore qs = es.getQualityStore();
        
        FileSource fs = FileSourceBuilder.test_rms(es);
        
        String entity = fs.importFile();
        qs.printIndex();
        System.out.println();
        try {
            System.out.println(qs.delete("FileSystem", "Filesize", entity));
            System.out.println(qs.delete("FileSystem", "Filesize", entity));
            System.out.println(qs.delete("FileSystem", "Filesize", entity));
            System.out.println(qs.delete("Filesystem", "Filesize", entity));
            System.out.println(qs.delete("FileSystem", "Filename", entity));
            System.out.println(qs.delete("FileSystem", "Filesize", "12342"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        qs.printIndex();
        
    }
}
