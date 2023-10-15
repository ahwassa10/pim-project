package model.attributive;

import java.util.Iterator;

import model.attributive.implementation.HashForest;
import model.attributive.specification.Forest;

public class Testing {
    public static void main(String[] args) {
        Forest<String> forest = new HashForest<>();
        
        
        forest.attachSingle("one", "root");
        forest.attachSingle("a", "root");
        forest.attachSingle("two", "one");
        forest.attachSingle("three", "one");
        forest.attachSingle("four", "one");
        forest.attachSingle("five", "one");
        
        Iterator<String> i = forest.iterateDFS("root");
        
        i.forEachRemaining(s -> System.out.println(s));
        
        Forest<String> other = new HashForest<>(forest.atTree("one"));
        other.iterateDFS("one").forEachRemaining(s -> System.out.println(s));
    }
}
