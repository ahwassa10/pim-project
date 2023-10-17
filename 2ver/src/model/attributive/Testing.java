package model.attributive;

import model.attributive.implementation.HashDenseMap;
import model.attributive.specification.DenseMap;

public class Testing {
    public static void main(String[] args) {
        DenseMap<String, String> map = new HashDenseMap<>();
        
        map.apply("hi", "hello");
        map.apply("hi", "there");
        map.apply("hi", "bye");
        map.apply("other", "bye");
        
        map.iterateProperties("bye").forEachRemaining(p -> System.out.println(p));
    }
}
