package model.trees;

import java.util.Iterator;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        Tree<String> tree = new HashTree<>("Root");
        
        System.out.println(tree.contains("Root"));
        System.out.println(tree.isRootNode("Root"));
        System.out.println(tree.isInteriorNode("Root"));
        System.out.println(tree.isLeafNode("Root"));
        System.out.println(tree.isParentNode("Root"));
        System.out.println(tree.isExteriorNode("Root"));
        System.out.println(tree.isChildNode("Root"));
        
        System.out.println();
        
        System.out.println(tree.contains("Test"));
        System.out.println(tree.isRootNode("Test"));
        System.out.println(tree.isInteriorNode("Test"));
        System.out.println(tree.isLeafNode("Test"));
        System.out.println(tree.isParentNode("Test"));
        System.out.println(tree.isExteriorNode("Test"));
        System.out.println(tree.isChildNode("Test"));
        
        List<String> l = List.of("one","two", "three");

    }
}
