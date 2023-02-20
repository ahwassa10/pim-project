package loop;

import model.Tag;

public class Prog {
	public static void main(String[] arg) {
		System.out.println(Tag.isValidTagName("hello"));
		System.out.println(Tag.isValidTagName("hello_"));
		System.out.println(Tag.isValidTagName("hello_There"));
		System.out.println(Tag.isValidTagName("__hello"));
		System.out.println(Tag.isValidTagName("hel_lo"));
		System.out.println(Tag.isValidTagName("hell1234o"));
		
	}
}
