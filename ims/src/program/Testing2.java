package program;

import java.nio.file.Path;

import util.Hashing;

public class Testing2 {
	public static void main(String[] args) {
		Path folder = Path.of("C:\\Users\\Primary\\Desktop\\import");
		try {
			System.out.println(Hashing.asString(Hashing.calculateSHA256(folder.resolve("3080ti.JPG"))));
			System.out.println(Hashing.asString(Hashing.calculateSHA256(folder.resolve("EndTimes.JPG"))));
			System.out.println(Hashing.asString(Hashing.calculateSHA256(folder.resolve("leetcodepfp.png"))));
			System.out.println(Hashing.asString(Hashing.calculateSHA256(folder.resolve("empty.txt"))));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
