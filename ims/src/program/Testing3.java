package program;

import java.util.List;

import quality.Key;

public class Testing3 {
    public static void main(String[] args) {
        List<String> testKeys = List.of("hello", "123", "he11o", "h#llo", "#", "", "1",
                "hello_there", "there", "there123", "hello there", "__hello--", "$hello",
                "$", ":bricks:", " ", "t t t -", "   ", "abc_123-def");
        
        for (String test : testKeys) {
            System.out.println(Key.isValid(test) + " " + test);
        }
    }
}
