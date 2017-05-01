
public class jminusminusTester {
	
<<<<<<< HEAD
	
	void switchTestMethod() {
		int i  = 0;
		switch(i) {
			case 0: {int p = 2;}
			case 1: {int p = 2;}
		}
	}

	void standardFor() throws IOException, IllegalArgumentException {
		for(int i = 0; i < 10; i = i + 1) {
			System.out.println(i);
		}
	}
	
	
	void standardForNoInit() throws IOException, IllegalArgumentException {
		int i = 0;
		for(; i < 10; i = i + 1) {
			System.out.println(i);
		}
	}
=======
	void testMethod() {
		int i  = 0;
		switch(i) {
			case 0:  
				i = i + 1;
			case 1: 
				i = i + 2;
			default: 
				i = i + 3;
>>>>>>> origin/master

		}
	}
}