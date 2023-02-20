package model;

import java.util.Set;

public class Tag extends Entity {
	private String tagName;
	
	private static Set<Character> valid_tag_name_characters = 
			Set.of('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				   'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
				   'w', 'x', 'y', 'z', '_');
	
	public static boolean isValidTagName(String potential_name) {
		for (int i = 0; i < potential_name.length(); i++) {
			char c = potential_name.charAt(i);
			if (!valid_tag_name_characters.contains(c)) {
				return false;
			}
		}
		return true;
	}
	
	Tag(String tagName) {
		this.tagName = tagName;
	}
	
	public String getTagName() {
		return tagName;
	}
}
