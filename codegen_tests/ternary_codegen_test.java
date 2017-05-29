import java.lang.System;

public class ternary_codegen_test {

	public static void main(String[] args) {
		int i = 1;
		int j = 0;
		
		String result = (i > j) ? "In IF side" : "In ELSE side";
		
		j=2;
	    String result2 = (i > j) ? "In IF side" : "In ELSE side";
		
		System.out.println(result);
		System.out.println(result2);
	}

}
