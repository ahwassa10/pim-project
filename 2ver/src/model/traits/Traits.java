package model.traits;

import java.util.Set;

import model.trees.HashTree;
import model.trees.IncorrigibleTree;

public final class Traits {
    private static final String EXIS_NAME = "Existence";
    private static final String EXIS_DESCRIPTION = "The root of the trait tree: Existence";
    
    public final Trait EXISTENCE;
    
    private final IncorrigibleTree<Trait> traitTree;
    
    Traits() {
        EXISTENCE = new AbstractTrait(Traits.EXIS_NAME, Traits.EXIS_DESCRIPTION) {
            public Trait getAnchor() {
                return this;
            }
        };
        
        traitTree = new HashTree<>(EXISTENCE);
    }
    
    public Trait create(String name, String description, Trait parent) {
        Trait.requireValidName(name);
        Trait.requireValidDescription(description);
        
        Trait newTrait = new AbstractTrait(name, description) {
            public Trait getAnchor() {
                return EXISTENCE;
            }
        };
        
        traitTree.grow(newTrait, parent);
        return newTrait;
    }
    
    public void delete(Trait trait) {
        traitTree.trim(trait);
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
        
        Trait meme = traits.create("meme", "", traits.EXISTENCE);
        Trait tech = traits.create("tech", "", traits.EXISTENCE);
        Trait work = traits.create("work", "", traits.EXISTENCE);
        
        System.out.println(meme);
        
        Trait funny = traits.create("funny", "", meme);
        Trait moai = traits.create("moai", "", meme);
        Trait gpu = traits.create("gpu", "", tech);
        
        Trait specificMoai = traits.create("specific moai", "", moai);
        
        System.out.println(traits.getDescendants(traits.EXISTENCE));
        //traits.printTree();
        
    }
}
