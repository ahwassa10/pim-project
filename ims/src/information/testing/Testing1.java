package information.testing;

import information.Name;
import sys_d.DMS;
import sys_d.DMSBuilder;
import sys_d.InfoType;
import sys_d.Types;

public class Testing1 {
	public static void main(String[] args) {
		DMS dms = DMSBuilder.test_dms();
		Types types = dms.getTypes();
		
		System.out.println(types.hasType("Name"));
		
	}
}
