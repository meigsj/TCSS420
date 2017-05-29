import java.lang.System;

public class long_test {
	    public void test (long l, long j) {
	    	l = l + j;
			System.out.println(l);
	    }
	
	
	    public static void main(String[] args) {
			long_test test = new long_test();
	    	long l = 3L;
			long j = 2L;
			l = l + j;
			System.out.println(l);
			test.test(l, j);
		}
}