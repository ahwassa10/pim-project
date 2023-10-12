package model.attributive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Testing {
    public static void main(String[] args) {
        Forest<String> forest = new HashForest<>();
        
        forest.attachSingle("one", "root");
        forest.attachSingle("two", "one");
        forest.attachSingle("three", "one");
        
        Iterator<String> i = forest.BFSIterator("root");
        
        
        
        
    }
}
