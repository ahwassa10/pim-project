package program;

import java.util.List;

import model.qualities.Name;
import model.qualities.Note;
import model.qualities.Score;
import structural.Pair;
import structural.Triple;

public class Testing1 {
	public static void main(String[] args) {
		Name n1 = Name.from("Hello there");
		Name n2 = Name.from("Hello there");
		
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
		
		Pair<String, Note> p4 = Pair.of("note", Note.from("A note"));
		Pair<String, Note> p5 = Pair.of("note", Note.from("A note"));
		Pair<Note, Note> p6 = Pair.of(Note.from("note"), Note.from("A note"));
		System.out.println(p4);
		System.out.println(p4.equals(p1));
		System.out.println(p4.equals(p5));
		System.out.println(p6.equals(p5));
		
		Triple<String, String, String> t1 =
				Triple.of("Hello", "there", "general");	
		Triple<String, String, Note> t2 =
				Triple.of("Owner", "note", Note.from("hello!"));
		Triple<String, String, String> t3 =
				Triple.of("Hello", "there", "general");
		
		System.out.println(t1);
		System.out.println(t2);
		
		List<Triple<String, String, String>> list = List.of(t1);
		System.out.println(list.contains(t3));
		// Should print true since Triples are value objects.
		
		Pair<String, Pair<String, Note>> pp = t2.asPairs();
		System.out.println(String.format("<Key: %s, Value: <Key: %s, Value: %s>>",
				pp.getKey(), pp.getValue().getKey(), pp.getValue().getKey()));
	
		
		Score s = Score.newScore(10);
		s.increment();
		s.increment();
		s.decrement();
		System.out.println(s.getScore());
		
	}
}
