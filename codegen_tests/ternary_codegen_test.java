import java.lang.System;

public class ternary_codegen_test {

	public static void main(String[] args) {
		int i = 0;
		int j = 0;
		
		if (i > 0 || j > 0) {
			System.out.print("Inside first if!");
		}
		
		do {
			j = j + 1;
			System.out.println("j: " + j);
		} while (j < 10);
		
		
		if (i > 0 || j > 0) {
			System.out.println("Inside second if!");
		}
		
		int x = (i > j) ? 5 : 1;
		System.out.println(x);
		System.out.println(i);
		System.out.println(j);
	}

}
