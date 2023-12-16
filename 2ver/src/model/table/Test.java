package model.table;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Integer> l = List.of(1);
        
        int sum = l.stream().reduce((i, j) -> (i + j)).get();
        
        System.out.println(sum);
    }
}
