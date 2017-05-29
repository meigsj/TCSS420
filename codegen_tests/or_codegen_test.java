import java.lang.System;
public class or_codegen_test {

	public static void main(String[] args) {
		boolean i = true;
		boolean j = false;
			
		if (i || j) {
			System.out.println("Passed!");
		} else {
				System.out.println("Failed...");
		}
			
		if (j || i) {
			System.out.println("Passed!");
		} else {
			System.out.println("Failed...");
		}
			
		if (j || j) {
			System.out.println("Failed...");
		} else {
			System.out.println("Passed!");
		}
			
		if (i || i) {
			System.out.println("Passed!");
		} else {
			System.out.println("Failed...");
		}
		
		// Expect k==3 at end
		int k = 0;	
        do {
        	k = k + 1;
        } until (j || k > 2);
        System.out.println(k);
        
        k = 0;
        do {
        	k = k + 1;
        } until (k > 2 || j);
        System.out.println(k);
        
        // Expect k==1 at end
		k = 0;	
        do {
        	k = k + 1;
        } until (i || k > 2);
        System.out.println(k);
		
        k = 0;	
        do {
        	k = k + 1;
        } until (k > 2 || i);
        System.out.println(k);
        
	}

}
