package information.testing;

import information.Name;
import sys_d.DMS;
import sys_d.DMSBuilder;
import sys_d.DataPair;
import sys_d.Info;
import sys_d.InfoType;
import sys_d.SimpleDataPair;
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
		
		InfoType note = types.getInfoType("Note");
		Info ni = note.asInfo("This is a note");
		System.out.println(ni);
		DataPair dpi = types.asDataPair(ni);
		System.out.println(dpi);
		Info ni2 = types.asInfo(dpi);
		System.out.println(ni2);
		System.out.println(ni.equals(ni2));
		
		DataPair dp3 = new DataPair() {
			public String getQualifier() {
				return "Note";
			}
			public String getData() {
				return "This is a note";
			}
		};
		System.out.println(dp3);
		System.out.println(dpi.equals(dp3));
		System.out.println(dp3.equals(dpi));
	}
}
