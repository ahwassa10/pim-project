package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class Traits {
    private final ImmutableTree<Trait> traitTree;
    
    public final Trait EXISTENCE = new Trait() {
        private final String description = "The root of the trait tree: existence";
        private final String name = "Existence";
        public String getDescription() {
            return description;
        }
        public String getName() {
            return name;
        }
        public Trait getSuperTrait() {
            return this;
        }
        public String toString() {
            return "Trait<Existence>";
        }
    };
    
    Traits() {
        traitTree = new ImmutableTree<>(this.EXISTENCE);
    }
    
    public Trait create(String name, String description, Trait superTrait) {
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
    
    public static void main(String[] args) {
        Traits traits = new Traits();
        System.out.println(traits.EXISTENCE);
        
        Trait meme = traits.create("meme", "", traits.EXISTENCE);
        Trait tech = traits.create("tech", "", traits.EXISTENCE);
        Trait work = traits.create("work", "", traits.EXISTENCE);
        
        Trait funny = traits.create("funny", "", meme);
        Trait moai = traits.create("moai", "", meme);
        
        System.out.println(moai.getSuperTraits());
    }
}
