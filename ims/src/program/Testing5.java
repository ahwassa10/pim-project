package program;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class Testing5 {
    public static void main(String[] args) {
        boolean b = true;
        b &= true;
        b &= false;
        b &= true;
        System.out.println(b);
        
        String h = "hello";
        System.out.println(h.equals(null));
        
        try {
            String out = Files.readString(Path.of("test/testing"));
        } catch (NoSuchFileException e) {
            System.out.println("first");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("second");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
