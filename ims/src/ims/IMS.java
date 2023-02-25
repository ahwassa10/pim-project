package ims;

import ims.inmemory.MemTable;
import ims.inmemory.MemTablePrinter;
import model.entities.Entity;

public class IMS {
	public static void main(String[] args) {
		MemTable table = new MemTable();
		
		Entity test = table.createEntity().setName("Test Entity").create();
		Entity potato = table.createEntity().setName("Potato").create();
		
		MemTablePrinter.print(table);
		System.out.println();
		
		table.removeEntity(test);
		MemTablePrinter.print(table);
		
	}
}
