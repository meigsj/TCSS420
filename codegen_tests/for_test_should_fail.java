import java.lang.System;

public class for_test_should_fail {

    public static void main(String[] args) {
		
		for(int i = 0; i < 5; i = i+1) {
			System.out.println("First Test ( <5 ): " + i);
		}
		
		System.out.println("Should not work! I is out of scope: " + i);
	}

}
