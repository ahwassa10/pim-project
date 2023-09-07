package model;

public abstract class AbstractTrait implements Trait {
    public String toString() {
        return "Trait<" + this.getName() + ">";
    }
}
