package program;

import sys_r.RMS;
import sys_r.RMSBuilder;

public class Testing {
	public static void main(String[] args) {
		RMS rms = RMSBuilder.test_rms();
		
		System.out.println(rms.getData());
	}
}
