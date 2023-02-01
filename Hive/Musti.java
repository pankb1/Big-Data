import java.util.StringTokenizer;
class Musti {
	public static void main (String args []) {
		String s = "53877 20170107 2.422 -82.61 35.49 -4.0 -11.3 -7.7 -7.4 6.4 2.55 C -4.3 -18.9 -10.1 90.6 54.1 69.2 -99.00 0.188 0.246 0.347 0.413 4.2 6.7 7.4 8.3 10.7 " ;
		 String sub =s.substring(38,46);
		 System.out.println(sub.trim());
		 String st[] = s.split("\\s+");
		 System.out.println(st[5]);
		 System.out.println(st[1].substring(0,4));
	}	 
}
