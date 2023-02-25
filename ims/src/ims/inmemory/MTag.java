package ims.inmemory;

import java.util.Set;

import model.entities.Tag;

public class MTag extends MemEntity implements Tag {
	private String tagName;
	
	MTag(String tagName) {
		this.tagName = tagName;
	}
	
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
	
	public String getTagName() {
		return tagName;
	}
}
