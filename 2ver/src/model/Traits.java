package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class Traits {
    private final Map<Trait, Set<Trait>> traitTree = new HashMap<>();
    
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
        traitTree.put(EXISTENCE, new HashSet<>());
        traitTree.get(EXISTENCE).add(EXISTENCE);
    }
    
    public void cut(NaturalTrait trait) {
        this.requireOwnership(trait);
        
        List<Trait> stack = new ArrayList<>();
        stack.add(trait);
        
        while (stack.size() != 0) {
            Trait aTrait = stack.get(stack.size()-1);
            
            if (traitTree.containsKey(aTrait)) {
                for (Trait subTrait : traitTree.get(aTrait)) {
                    stack.add(subTrait);
                }
            } else {
                Trait superTrait = aTrait.getSuperTrait();
                traitTree.get(superTrait).remove(aTrait);
                stack.remove(stack.size() - 1);
                if (traitTree.get(superTrait).size() == 0) {
                    traitTree.remove(superTrait);
                }
            }
        }
    }
    
    public Map<Trait, Set<Trait>> getTraitTree() {
        return traitTree;
    }
    
    public NaturalTrait grow(String name, String description, Trait superTrait) {
        this.requireOwnership(superTrait);
        NaturalTrait nt = new NaturalTrait(name, description, superTrait);
        traitTree.computeIfAbsent(superTrait, key -> new HashSet<>()).add(nt);
        return nt;
    }
    
    public boolean owns(Trait trait) {
        Objects.requireNonNull(trait, "Trait cannot be null");
        
        Trait superTrait = trait.getSuperTrait();
        
        return traitTree.containsKey(superTrait) && traitTree.get(superTrait).contains(trait);
    }
    
    public Trait requireOwnership(Trait trait) {
        if (!this.owns(trait)) {
            String msg = String.format("%s does not belong to this trait tree", trait);
            throw new IllegalArgumentException(msg);
        }
        return trait;
    }
    
    public void trim(NaturalTrait trait) {
        this.requireOwnership(trait);
        
        if (traitTree.containsKey(trait)) {
            String msg = String.format("%s cannot have subtraits in the trait tree", trait);
            throw new IllegalArgumentException(msg);
        } else {
            Trait superTrait = trait.getSuperTrait();
            traitTree.get(superTrait).remove(trait);
            if (traitTree.get(superTrait).size() == 0) {
                traitTree.remove(superTrait);
            }
        }
    }
    
    public static void main(String[] args) {
        Traits traits = new Traits();
        System.out.println(traits.EXISTENCE);
        
        NaturalTrait trait = traits.grow("test", "", traits.EXISTENCE);
        NaturalTrait trait2 = traits.grow("test2", "", trait);
        NaturalTrait trait3 = traits.grow("specific trait", "", trait2);
        NaturalTrait trait4 = traits.grow("meme", "", traits.EXISTENCE);
        
        System.out.println(traits.getTraitTree());
        
        traits.cut(trait);
        traits.cut(trait4);
        
        System.out.println(traits.getTraitTree());
        
    }
}
