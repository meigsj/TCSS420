import java.lang.System;

public class basic_for_test {
	
	public void testNoCond(String test) {
		for(int i = 0; ; i = i+1) {
			System.out.println(test + " Test ( <5 ): " + i);
			if (i >= 5) {
				return;
			}
		}
	}
	
	public void testNoInitCond(String test) {
		int i= 0;
		for(; ; i = i+1) {
			System.out.println(test + " Test ( <5 ): " + i);
			if (i >= 5) {
				return;
			}
		}
	}
	
	public void testNoCondUpdate(String test) {
		for(int i = 0; ;) {
			System.out.println(test + " Test ( <5 ): " + i);
			i = i + 1;
			if (i >= 5) {
				return;
			}
		}
	}
	
	public void testNoExpressions(String test) {
		int i = 0;
		for(; ;) {
			System.out.println("Fourth Test ( <5 ): " + i);
			i = i + 1;
			if (i >= 5) {
				return;
			}
		}
	}
	
	
	public static void main(String[] args) {
		basic_for_test test = new basic_for_test();
		
		for(int i = 0; i < 5; i = i+1) {
			System.out.println("First Test ( <5 ): " + i);
		}
		
		for(int i = 0; i < 5;) {
			i = i + 1;
			System.out.println("Second Test ( <5 ): " + i);
		}
		
		int j = 0;
		for (;j<5; j=j+1) {
			System.out.println("Third Test ( <5 ): " + j);
		}
		
		test.testNoCond("Fourth");
		
		j = 0;
		for (;j<5;) {
			System.out.println("Fifth Test ( <5 ): " + j);
			j = j+1;
		}
		
		test.testNoInitCond("Sixth");
		test.testNoCondUpdate("Seventh");
		test.testNoExpressions("Eighth");
	}
}
