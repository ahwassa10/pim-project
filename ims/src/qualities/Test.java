package qualities;

import qualities.naming.StringName;
import qualities.naming.NameQuality;

public class Test {
	public static void main(String[] args) {
		NameQuality n = new NameQuality(StringName.from("Hello"));
		System.out.println(n.getQualityType());
		
		AbstractQualityPair<StringName> n2 = n;
		
		System.out.println(n2.getQualityType());
	}
}
