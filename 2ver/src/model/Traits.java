package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class Traits {
    private final class NaturalTrait extends AbstractTrait {
        private final String description;
        private final String name;
        private final Trait superTrait;
        
        private NaturalTrait(String description, String name, Trait superTrait) {
            this.description = description;
            this.name = name;
            this.superTrait = superTrait;
        }
        
        public String getDescription() {
            return description;
        }
        
        public String getName() {
            return name;
        }
        
        public Trait getSuperTrait() {
            return superTrait;
        }
    }
    
    public final Trait EXISTENCE = new AbstractTrait() {
        public String getDescription() {
            return "The root of the trait tree: Existence";
        }
        
        public String getName() {
            return "Existence";
        }
        
        public Trait getSuperTrait() {
            return this;
        }
    };
    
    private final IncorrigibleTree<Trait> traitTree = new IncorrigibleTree<>(this.EXISTENCE);
    
    Traits() {}
    
    public Trait create(String name, String description, Trait superTrait) {
        Trait.requireValidName(name);
        Trait.requireValidDescription(description);
        traitTree.requirePresence(superTrait);
        
        NaturalTrait nt = new NaturalTrait(name, description, superTrait);
        return traitTree.grow(nt, superTrait);
    }
    
    public void delete(Trait trait) {
        traitTree.requirePresence(trait);
        
        if (traitTree.containsLeaf(trait)) {
            traitTree.trim(trait);
        } else {
            traitTree.cut(trait);
        }
    }
    
    public void printTree() {
        System.out.println(traitTree);
    }
    
    public static void main(String[] args) {
        Traits traits = new Traits();
        System.out.println(traits.EXISTENCE);
        System.out.println(traits.EXISTENCE.getSuperTrait());
        
        Trait meme = traits.create("meme", "", traits.EXISTENCE);
        Trait tech = traits.create("tech", "", traits.EXISTENCE);
        Trait work = traits.create("work", "", traits.EXISTENCE);
        
        Trait funny = traits.create("funny", "", meme);
        Trait moai = traits.create("moai", "", meme);
        Trait gpu = traits.create("gpu", "", tech);
        
        System.out.println(moai.getSuperTraits());
        traits.printTree();
        
    }
}
