package program;

import java.util.List;

import information.name.StringName;
import information.note.StringNote;
import structural.Score;
import util.Pair;
import util.Triple;

public class Testing1 {
	public static void main(String[] args) {
		StringName n1 = StringName.from("Hello there");
		StringName n2 = StringName.from("Hello there");
		
		System.out.println(n1.equals(n2));
		System.out.println(n1.getQualityType());
		System.out.println(n1);
		
		Pair<String, String> p1 = Pair.of("Hello", "there");
		Pair<String, String> p2 = Pair.of("Hello", "there");
		Pair<String, String> p3 = Pair.of("hello", "hi");
		
		System.out.println(p1);
		System.out.println(p3);
		
		System.out.println(p1.equals(p2));
		System.out.println(p2.equals(p1));
		System.out.println(p2.equals(p3));
		System.out.println(p3.equals(p1));
		
		Pair<String, StringNote> p4 = Pair.of("note", StringNote.from("A note"));
		Pair<String, StringNote> p5 = Pair.of("note", StringNote.from("A note"));
		Pair<StringNote, StringNote> p6 = Pair.of(StringNote.from("note"), StringNote.from("A note"));
		System.out.println(p4);
		System.out.println(p4.equals(p1));
		System.out.println(p4.equals(p5));
		System.out.println(p6.equals(p5));
		
		Triple<String, String, String> t1 =
				Triple.of("Hello", "there", "general");	
		Triple<String, String, StringNote> t2 =
				Triple.of("Owner", "note", StringNote.from("hello!"));
		Triple<String, String, String> t3 =
				Triple.of("Hello", "there", "general");
		
		System.out.println(t1);
		System.out.println(t2);
		
		List<Triple<String, String, String>> list = List.of(t1);
		System.out.println(list.contains(t3));
		// Should print true since Triples are value objects.
		
		Pair<String, Pair<String, StringNote>> pp = t2.asPairs();
		System.out.println(String.format("<Key: %s, Value: <Key: %s, Value: %s>>",
				pp.getKey(), pp.getValue().getKey(), pp.getValue().getKey()));
	
		
		Score s = Score.newScore(10);
		s.increment();
		s.increment();
		s.decrement();
		System.out.println(s.getScore());
		
	}
}
