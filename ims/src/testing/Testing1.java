package testing;

import information.AbstractInfoPair;
import information.InfoTypeName;
import information.name.Name;
import information.note.Note;

public class Testing1 {
	public static void main(String[] args) {
		System.out.println(InfoTypeName.isValidInfoType("Name"));
		System.out.println(InfoTypeName.isValidInfoType("File-Name"));
		System.out.println(InfoTypeName.isValidInfoType("File-name"));
		System.out.println(InfoTypeName.isValidInfoType("-Name"));
		System.out.println(InfoTypeName.isValidInfoType("name"));
		System.out.println(InfoTypeName.isValidInfoType(""));
	}
}
