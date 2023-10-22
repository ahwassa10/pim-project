package model.attributive;

import java.util.HashMap;
import java.util.Map;

import model.attributive.implementation.HashFunctionMap;
import model.attributive.specification.FunctionMap;

public class Testing {
    public static void main(String[] args) {
        FunctionMap<Integer, String> map = new HashFunctionMap<>();
        map.apply(1, "hi");
        map.apply(2, "hi");
        map.apply(3, "hi");
        
        map.remove(1, "hi");
        
        map.apply(1, "hello");
        map.apply(10, "hello");
        
        System.out.println(map.getAttribution(1));
        System.out.println(map.getAttribution(2));
        System.out.println(map.getAttribution(3));
        System.out.println(map.getAttribution(10));
        System.out.println(map.getAttributes("hi"));
        System.out.println(map.getAttributes("hello"));
        
        Map<String, String> m = new HashMap<>();
        m.con
        
    }
}
