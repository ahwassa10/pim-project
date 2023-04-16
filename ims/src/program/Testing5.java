package program;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Testing5 {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        set.add("red");
        set.add("green");
        set.add("blue");
        set.add("yellow");
        
        for (Iterator<String> i = set.iterator(); i.hasNext();) {
            String content = i.next();
            
            set.remove(content);
        }
    }
}
