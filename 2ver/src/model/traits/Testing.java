package model.traits;

public class Testing {
    public static void main(String[] args) {
        ExistenceTrait traits = new ExistenceTrait();
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
