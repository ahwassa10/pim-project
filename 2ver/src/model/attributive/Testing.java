package model.attributive;

public class Testing {
    public static void main(String[] args) {
        Forest<String> forest = new HashForest<>();
        
        forest.attachSingle("one", "root");
        forest.attachSingle("two", "one");
        forest.attachSingle("three", "one");
        
        
    }
}
