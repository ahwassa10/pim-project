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
		
		Info i = name.fromData("Test name");
		String data = i.asData();
		DataPair dp = i.asDataPair();
		System.out.println(dp.getQualifier() + " " + dp.getData());
		System.out.println(data);
	}
}
