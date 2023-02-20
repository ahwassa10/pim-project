package loop;

import model.IMS;
import model.Tag;

public class Prog {
	public static void main(String[] arg) {
		IMS library = new IMS();
		Tag tech_tag = library.createTag("tech");
		System.out.println(tech_tag.getTagName());
		library.createName("Ethoslab");
	}
}
