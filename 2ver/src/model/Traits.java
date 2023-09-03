package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class Traits {
    private final Map<Trait, Set<NaturalTrait>> traitTree = new HashMap<>();
    
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
        traitTree.put(this.EXISTENCE, new HashSet<>());
    }
    
    public NaturalTrait create(String name, String description, Trait superTrait) {
        this.requireOwnership(superTrait);
        NaturalTrait nt = new NaturalTrait(name, description, superTrait);
        traitTree.computeIfAbsent(superTrait, key -> new HashSet<>()).add(nt);
        return nt;
    }
    
    public void delete(NaturalTrait trait) {
        this.requireOwnership(trait);
        
        List<NaturalTrait> deletions = new ArrayList<>();
        deletions.add(trait);
        
        while (deletions.size() != 0) {
            Trait aTrait = deletions.get(deletions.size() - 1);
            if (traitTree.containsKey(aTrait)) {
                deletions.addAll(traitTree.get(aTrait));
            } else {
                deletions.remove(deletions.size() - 1);
            }
        }
    }
    
    public Map<Trait, Set<NaturalTrait>> getTraitTree() {
        return traitTree;
    }
    
    public boolean owns(Trait trait) {
        Objects.requireNonNull(trait, "Trait cannot be null");
        return trait.getAnchorTrait() == this.EXISTENCE;
    }
    
    public Trait requireOwnership(Trait trait) {
        if (!this.owns(trait)) {
            String msg = String.format("%s does not belong to this trait tree", trait);
            throw new IllegalArgumentException(msg);
        }
        return trait;
    }
    
    public static void main(String[] args) {
        Traits traits = new Traits();
        System.out.println(traits.EXISTENCE);
        
        Trait trait = traits.create("test", "", traits.EXISTENCE);
        Trait trait2 = traits.create("test2", "", trait);
        Trait trait3 = traits.create("specific trait", "", trait2);
        Trait trait4 = traits.create("meme", "", traits.EXISTENCE);
        
        System.out.println(trait);
        System.out.println(trait.getAnchorTrait());
        
        System.out.println(trait3.getSuperTraits());
        
        System.out.println(traits.getTraitTree());
    }
}
