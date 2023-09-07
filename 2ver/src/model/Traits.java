package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class Traits {
    private final class TraitStructure extends AbstractTrait {
        private final String description;
        private final String name;
        
        private TraitStructure(String description, String name) {
            this.description = description;
            this.name = name;
        }
        
        public Trait getAnchor() {
            return Traits.this.EXISTENCE;
        }
        
        public String getDescription() {
            return description;
        }
        
        public String getName() {
            return name;
        }
        
        public Trait getParent() {
            return Traits.this.traitTree.getParent(this);
        }
    }
    
    public final Trait EXISTENCE = new AbstractTrait() {
        public String getDescription() {
            return "The root of the trait tree: Existence";
        }
        
        public Trait getAnchor() {
            return this;
        }
        
        public String getName() {
            return "Existence";
        }
        
        public Trait getParent() {
            return this;
        }
    };
    
    private final IncorrigibleTree<Trait> traitTree = new IncorrigibleTree<>(this.EXISTENCE);
    
    Traits() {}
    
    public Trait create(String name, String description, Trait parent) {
        Trait.requireValidName(name);
        Trait.requireValidDescription(description);
        traitTree.requirePresence(parent);
        
        TraitStructure ts = new TraitStructure(description, name);
        return traitTree.grow(ts, parent);
    }
    
    public void delete(Trait trait) {
        traitTree.requirePresence(trait);
        
        if (traitTree.containsLeaf(trait)) {
            traitTree.trim(trait);
        } else {
            traitTree.cut(trait);
        }
    }
    
    public Set<Trait> getDescendants(Trait trait) {
        // getChildren() already returns an unmodifiableSet instance
        return traitTree.getChildren(trait);
    }
    
    public void printTree() {
        System.out.println(traitTree);
    }
    
    public static void main(String[] args) {
        Traits traits = new Traits();
        System.out.println(traits.EXISTENCE);
        System.out.println(traits.EXISTENCE.getParent());
        
        Trait meme = traits.create("meme", "", traits.EXISTENCE);
        Trait tech = traits.create("tech", "", traits.EXISTENCE);
        Trait work = traits.create("work", "", traits.EXISTENCE);
        
        System.out.println(meme);
        
        Trait funny = traits.create("funny", "", meme);
        Trait moai = traits.create("moai", "", meme);
        Trait gpu = traits.create("gpu", "", tech);
        
        Trait specificMoai = traits.create("specific moai", "", moai);
        
        System.out.println(specificMoai.getAncestors());
        //traits.printTree();
        
    }
}
