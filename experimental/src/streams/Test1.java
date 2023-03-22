package streams;

import java.util.List;
import java.util.Map;

public class Test1 {
	public static void main(String[] args) {
		List<String> l = List.of("Test", "hello", "there", "one");
		List<String> l2 = l.stream().map(i -> i.toUpperCase()).toList();
		
		System.out.println(l);
		System.out.println(l2);
		
		Map<String, Map<String, String>> map =
				Map.of("User", Map.of("Name", "Minkey", "Note", "This is a note"),
					   "FS", Map.of("Filename", "minkey.png", "Filesize", "1234"));
		
		System.out.println(map);
		
		map.entrySet().stream().forEach(i -> System.out.println(i));
	}
}
