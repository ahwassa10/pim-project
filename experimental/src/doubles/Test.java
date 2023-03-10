package doubles;

public class Test {
	public static boolean getBitAt(long l, int index) {
		long bit = (l >> index) & 1;
		return (bit == 1);
		
	}
	
	public static void printLong(long l) {
		// Print bits from left (msb) to right (lsb)
		for (int i = 63; i >= 0; i--) {
			
			if (i == 62 || i == 51) {
				System.out.print("_");
			}
			
			boolean bit = getBitAt(l, i);
			System.out.print(bit ? "1" : "0");
		}
		System.out.println();
	}
	
	public static void printDouble(double d) {
		// You need to cast the double to a void pointer
		// and then the void pointer to a long. This just
		// basically says "change the type but don't mess
		// with the bits". 
		// l = (long) ((void *) d)
		printLong(Double.doubleToRawLongBits(d));
	}
	
	public static void main(String[] args) {
		double d = -0.15625;
		double d2 = 10;
		
		printDouble(d);
		printDouble(d2);
	}
}
