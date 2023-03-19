package information.testing;

import information.Name;
import sys_d.DMS;
import sys_d.DMSBuilder;
import sys_d.DataPair;
import sys_d.Info;
import sys_d.InfoType;
import sys_d.Types;

public class Testing1 {
	public static void main(String[] args) {
		DMS dms = DMSBuilder.test_dms();
		Types types = dms.getTypes();

		System.out.println(types.listTypeNames());
		
		InfoType name = types.getInfoType("Name");
		System.out.println(name);
		
		Info i = name.asInfo("Testing Information");
		System.out.println(i);
		
		String d = name.asData(i);
		System.out.println(d);
		
		DataPair dp = types.asDataPair(i);
		System.out.println(dp);
		
		Info i2 = types.asInfo(dp);
		System.out.println(i2);
		
		System.out.println(i.equals(i2));
	}
}
