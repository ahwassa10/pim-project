package quality;

import structural.Name;

public class Test {
	public static void main(String[] args) {
		NameQuality n = new NameQuality(Name.from("Hello"));
		System.out.println(n.getQualityType());
		
		AbstractQualityPair<Name> n2 = n;
		
		System.out.println(n2.getQualityType());
	}
}
