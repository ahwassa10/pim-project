package ims.inmemory;

import model.entities.Entity;

public class MemTablePrinter {
	private MemTablePrinter() {}
	
	public static void print(MemTable table) {
		for (Entity subject : table.getEntities()) {
			System.out.print(subject);
			System.out.print("{");
			for (Entity object : table.getRelationship(subject)) {
				System.out.print(object);
				System.out.print(", ");
			}
			System.out.println("}");
		}
	}
}
