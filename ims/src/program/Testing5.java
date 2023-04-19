package program;

import java.util.List;

import entity.Keys;

public class Testing5 {
    public static void main(String[] args) {
        List<String> testKeys = List.of("hello", "123", "he11o", "h#llo", "#", "", "1",
                "hello_there", "there", "there123", "hello there", "__hello--", "$hello",
                "$", ":bricks:", " ", "t t t -", "   ", "abc_123-def", "user.name",
                "user.name.1234", "filesystem.file_size.entity-2431", "....", "user$name",
                "general.tag", "general.tag.rating123", "general.selection-tag.content-type");
        
        for (String key : testKeys) {
            boolean valid = Keys.isValid(key);
            String msg1 = String.format("%s -> %s",
                    key, valid);
            System.out.println(msg1);
            
            if (!valid) {
                continue;
            }
            
            String msg = String.format("%s, Key: %s, Stem: %s, Tip: %s, Combined: %s",
                    key.equals(Keys.combine(Keys.getStem(key), Keys.getTip(key))),
                    key,
                    Keys.getStem(key),
                    Keys.getTip(key),
                    Keys.combine(Keys.getStem(key), Keys.getTip(key)));
            System.out.println(msg);
            
        }
    }
}
